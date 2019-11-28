package de.upb.sede.composition.graphs.typing;

import de.upb.sede.ISDLLookupService;
import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.InstructionIndexer;
import de.upb.sede.composition.graphs.nodes.IIndexedInstruction;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.graphs.types.IFieldType;
import de.upb.sede.exec.*;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;
import de.upb.sede.util.Streams;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static de.upb.sede.composition.graphs.typing.Locals.getServiceType;
import static de.upb.sede.composition.graphs.typing.Locals.isService;

class TypeCheckStep {

    private TypeJournal typeJournal;

    private InstructionMethodResolver instructionMethodResolver;

    private InstructionIndexer instructions;

    private ISDLLookupService lookupService;


    TypeCheckStep(TypeJournal typeJournal, InstructionMethodResolver instructionMethodResolver, InstructionIndexer instructions, ISDLLookupService lookupService) {
        this.typeJournal = typeJournal;
        this.instructionMethodResolver = instructionMethodResolver;
        this.instructions = instructions;
        this.lookupService = lookupService;
    }

    void checkAll() {
        for (IIndexedInstruction indexedInst : instructions) {
            typeCheckContext(indexedInst);
        }
    }

    private void typeCheckContext(IIndexedInstruction indexedInstruction) {

        IInstructionNode inst = indexedInstruction.getInstruction();
        long index = indexedInstruction.getIndex();
//        TypeJournalPage currentPage = typeJournal.getPageAt(index);
        TypeJournalPage nextPage = typeJournal.getPageAfterInst(index);
        InstructionMethodResolver.Indexed indexedInstResolution;
        indexedInstResolution = instructionMethodResolver.ofIndex(index);
        try {
            recordCheckedTypes(inst, nextPage, indexedInstResolution);
        } catch(NullPointerException | TypeCheckException typeError) {
            throw new TypeCheckException(indexedInstruction, typeError);
        }
    }

    private void recordCheckedTypes(IInstructionNode inst, TypeJournalPage typeContext, InstructionMethodResolver.Indexed iMR) {

        String serviceContextQualifier;

        @Nullable // null indicates a static invocation
        IFieldType fieldContext;

        if(inst.getContextIsFieldFlag()) {
            /*
             * type check field
             */
            String contextFieldName = inst.getContext();
            IFieldType fieldType = typeContext.getFieldType(contextFieldName);
            if(fieldType == null) {
                throw TypeCheckException.undefinedField(contextFieldName);
            } else if(!isService(fieldType.getFieldType())) {
                throw TypeCheckException.unexpectedFieldType(fieldType, "Service Instance", "Service methods can only be invoked with service instances as a context.");
            }
            fieldContext = fieldType;
            serviceContextQualifier = getServiceType(fieldType.getFieldType()).getQualifier();
        } else {
            serviceContextQualifier = inst.getContext();
            fieldContext = null;
        }

        /*
         * Lookup the service description of the context
         */
        IServiceRef contextServiceRef = IServiceRef.of(null, serviceContextQualifier);
        Optional<IServiceDesc> serviceContextOpt = lookupService.lookup(contextServiceRef);
        IServiceDesc service = serviceContextOpt
            .orElseThrow(() -> TypeCheckException.unknownType(serviceContextQualifier, "service"));
        IServiceRef fullServiceRef;
        fullServiceRef = IServiceRef.of(
            lookupService.lookupCollection(contextServiceRef)
                .orElseThrow(() -> new RuntimeException("Implementation error.")).getQualifier(),
            serviceContextQualifier);

        /*
         * Lookup the method description
         */
        String methodQualifier = inst.getMethod();
        IMethodRef methodRef = IMethodRef.of(fullServiceRef, methodQualifier);
        Optional<IMethodDesc> lookedUpMethod = lookupService.lookup(methodRef);
        IMethodDesc method = lookedUpMethod
            .orElseThrow(()-> TypeCheckException.unknownType(serviceContextQualifier + "::" + methodQualifier,
                "method"));


        /*
         * Select signature that matches the amount of parameters
         */
        ISignatureDesc signature = getMatchingSignature(serviceContextQualifier, method, inst);
        assert signature.getInputs().size() == inst.getParameterFields().size();
        /*
         * Type-check signature
         */
        int inputParamSize = signature.getInputs().size();
        for (int i = 0; i < inputParamSize; i++) {
            String expectedInputType = signature.getInputs().get(i).getType();
            String instParam = inst.getParameterFields().get(i);

            Objects.requireNonNull(expectedInputType);
            Objects.requireNonNull(instParam);

            TypeCoercion typeCoercion;
            if(FMCompositionParser.isConstant(instParam)) {
                /*
                 * The given param is a constant, e.g. a number like `5`
                 */
                typeCoercion = constantParam(instParam, expectedInputType);
            } else {
                /*
                 * The given param is a field, e.g. `a`
                 * We look into the typing context to get the type of the field:
                 */
                IFieldType paramType = typeContext.getFieldType(instParam);
                if(paramType == null) {
                    throw TypeCheckException.undefinedField(instParam);
                }
                typeCoercion = castValue(paramType, expectedInputType);
            }
        }

        iMR.setMethodCognition(new MethodCognition(service, method, signature, methodRef));

    }


    private ISignatureDesc getMatchingSignature(String serviceContextQualifier, IMethodDesc method, IInstructionNode inst) {
        List<ISignatureDesc> signatures = method.getSignatures();
        boolean instructionIsAssignment = inst.getFieldName() != null;
        Stream<ISignatureDesc> matchingSignatures = signatures.stream()
            // method input must match in size:
            .filter(signature -> signature.getInputs().size() == inst.getParameterFields().size())
            // method must have at least one output if instruction is an assingment to a field:
            .filter(signature -> !instructionIsAssignment || !signature.getOutputs().isEmpty());
        // Only a single method has to match:
        Optional<ISignatureDesc> matchingSignature = Streams.pickOneOrNone(matchingSignatures);

        return matchingSignature.orElseThrow(() ->
            TypeCheckException.unknownMethodSignature(serviceContextQualifier, method.getQualifier(), inst));
    }

    private TypeCoercion constantParam(String constant, String targetPrimType) {
        /*
         * The parameter is a constant.
         * Replace the input type by the primitive type:
         */
        TypeCoercion typeCoercion = TypeCoercion.primType(constant);
        if(!typeCoercion.getResultType().equals(targetPrimType)) {
            throw TypeCheckException.unexpectedConstantType(constant, typeCoercion.getSourceType(), targetPrimType,
                "The expected type");
        }
        return typeCoercion;
    }

    private TypeCoercion castValue(IFieldType field, String targetType) {
        String sourceType = field.getFieldType().getTypeQualifier();
        IDataTypeRef sourceTypeRef = IDataTypeRef.of(sourceType);
        Optional<IDataTypeDesc> sourceTypeDescOpt = lookupService.lookup(sourceTypeRef);
        if(!sourceTypeDescOpt.isPresent()) {
            throw TypeCheckException.unknownType(field);
        }
        if(sourceType.equals(targetType)) {
            /*
             * No type coercion is needed:
             */
            return TypeCoercion.sameType(sourceType);
        }
        IDataTypeRef targetTypeRef = IDataTypeRef.of(targetType);
        Optional<IDataTypeDesc> targetTypeDescOpt = lookupService.lookup(targetTypeRef);
        if(!targetTypeDescOpt.isPresent()) {
            throw TypeCheckException.unknownType(targetType, "data type");
        }
        String sourceSemType = sourceTypeDescOpt.get().getSemanticType();
        String targetSemType = sourceTypeDescOpt.get().getSemanticType();
        if(!sourceSemType.equals(targetSemType)) {
            throw TypeCheckException.nonMatchingSemanticType(sourceType, sourceSemType, targetType, targetSemType);
        }
        return TypeCoercion.typeCast(sourceType, targetType, sourceSemType);
    }

}
