package de.upb.sede.composition.typing;

import de.upb.sede.ISDLLookupService;
import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.IMethodCognition;
import de.upb.sede.composition.ITypeCoercion;
import de.upb.sede.composition.InstructionIndexer;
import de.upb.sede.composition.IIndexedInstruction;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.graphs.types.*;
import de.upb.sede.core.PrimitiveType;
import de.upb.sede.exec.*;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;
import de.upb.sede.util.Streams;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static de.upb.sede.util.TypeUtil.getServiceType;
import static de.upb.sede.util.TypeUtil.isService;

class TypeCheckStep {

    private TypeJournal typeJournal;

    private InstIndexMap<IMethodCognition> methodCognitionMap;

    private InstructionIndexer instructions;

    private ISDLLookupService lookupService;

    TypeCheckStep(TCOutput output, InstructionIndexer instructions, ISDLLookupService lookupService) {
        this.methodCognitionMap = output.getMethodCognitionMap();
        this.typeJournal = output.getJournal();
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
        Consumer<IMethodCognition> indexedInstResolution;
        indexedInstResolution = methodCognitionMap.setter(index);
        try {
            typeCheckInstruction(inst, nextPage, indexedInstResolution);
        } catch(NullPointerException | TypeCheckException typeError) {
            throw new TypeCheckException(indexedInstruction, typeError);
        }
    }

    private void typeCheckInstruction(IInstructionNode inst, TypeJournalPage typeContext, Consumer<IMethodCognition> instMethodCognition) {

        String serviceContextQualifier;

        @Nullable // null indicates a static invocation
        TypeClass fieldContext;

        if(inst.getContextIsFieldFlag()) {
            /*
             * type check field
             */
            String contextFieldName = inst.getContext();
            TypeClass fieldType = typeContext.getFieldType(contextFieldName);
            if(fieldType == null) {
                throw TypeCheckException.undefinedField(contextFieldName);
            } else if(!isService(fieldType)) {
                throw TypeCheckException.unexpectedFieldType(contextFieldName, fieldType, "Service Instance", "Service methods can only be invoked with service instances as a context.");
            }
            fieldContext = fieldType;
            serviceContextQualifier = getServiceType(fieldType).getQualifier();
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
         * Type-check signature by creating type coercions.
         *
         * If there exists a list of TypeCoercion then the parameter signature type checks.
         */
        List<ITypeCoercion> parameterTypeCoercions = coerceParameters(signature, inst, typeContext);
        IMethodCognition mc = de.upb.sede.composition.MethodCognition.builder()
            .serviceDesc(service)
            .methodDesc(method)
            .signatureDesc(signature)
            .methodRef(methodRef)
            .parameterTypeCoersion(parameterTypeCoercions).build();
        instMethodCognition.accept(mc);

        if(inst.isAssignment()) {
            /*
             * Instruction assigns to a field.
             * Record the resulting type
             */
            String assignedField = inst.getFieldName();
            if(signature.getOutputs().isEmpty()) {
                throw TypeCheckException.methodNoReturnValue(method.getQualifier(), assignedField);
            }
            IMethodParameterDesc methodOutput = signature.getOutputs().get(0);
            String returnType = methodOutput.getType();
            TypeClass fieldType;
            try {
                fieldType = getTypeClassOf(returnType);
            } catch (TypeCheckException e) {
                throw new TypeCheckException(
                    String.format("Error classifying return type of method %s::%s",
                        service.getQualifier(), method.getQualifier() ),
                    e);
            }
            typeContext.setFieldType(assignedField, fieldType);
        }

    }

    private TypeClass getTypeClassOf(String typeQualifier) {
        /*
         * Assume type is a primitive qualifier, i.e. "Number", "String", "Bool"
         */
        Optional<PrimitiveType> primTypeOpt = PrimitiveType.insensitiveValueOf(typeQualifier);
        if(primTypeOpt.isPresent()) {
            /*
             * Method cannot declare the NULL class as its return value.
             */
            if(primTypeOpt.get() == PrimitiveType.NULL) {
                throw TypeCheckException.methodReturnsNullClass();
            }
            IPrimitiveValueType primValType = PrimitiveValueType.builder()
                .primitiveType(primTypeOpt.get()).build();
            return primValType;
        }
        /*
         * Assume type is a service type qualifier.
         */
        IServiceRef serviceRef = IServiceRef.of(null, typeQualifier);
        Optional<IServiceDesc> serviceDescOpt = lookupService.lookup(serviceRef);
        if(serviceDescOpt.isPresent()) {
            IServiceInstanceType serviceInstanceType = ServiceInstanceType.builder()
                .qualifier(typeQualifier)
                .build();
            return serviceInstanceType;
        }
        /*
         * The last case remaining is a data value type.
         */
        IDataTypeRef dataTypeRef = IDataTypeRef.of(typeQualifier);
        Optional<IDataTypeDesc> dataTypeOpt = lookupService.lookup(dataTypeRef);
        if(dataTypeOpt.isPresent()) {
            IDataValueType dataValueType = DataValueType.builder()
                .qualifier(typeQualifier)
                .build();
            return dataValueType;
        }

        /*
         * The type qualifier was not recognized..
         */
        throw TypeCheckException.unknownType(typeQualifier, "type");
    }

    private List<ITypeCoercion> coerceParameters(ISignatureDesc signature, IInstructionNode inst, TypeJournalPage typeContext) {
        List<ITypeCoercion> parameterTypeCoercion = new ArrayList<>();
        int inputParamSize = signature.getInputs().size();
        for (int i = 0; i < inputParamSize; i++) {
            String expectedInputType = signature.getInputs().get(i).getType();
            String instParam = inst.getParameterFields().get(i);

            Objects.requireNonNull(expectedInputType);
            Objects.requireNonNull(instParam);

            ITypeCoercion typeCoercion;
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
                TypeClass paramType = typeContext.getFieldType(instParam);
                if(paramType == null) {
                    throw TypeCheckException.undefinedField(instParam);
                }
                typeCoercion = castValue(paramType.getTypeQualifier(), expectedInputType);
            }
            parameterTypeCoercion.add(typeCoercion);
        }
        return parameterTypeCoercion;
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

    private ITypeCoercion constantParam(String constant, String targetPrimType) {
        /*
         * The parameter is a constant.
         * Replace the input type by the primitive type:
         */
        ITypeCoercion typeCoercion = primType(constant);
        if(!typeCoercion.getResultType().equals(targetPrimType)) {
            throw TypeCheckException.unexpectedConstantType(constant, typeCoercion.getSourceType(), targetPrimType,
                "The expected type");
        }
        return typeCoercion;
    }

    private ITypeCoercion castValue(String sourceType, String targetType) {
        IDataTypeRef sourceTypeRef = IDataTypeRef.of(sourceType);
        Optional<IDataTypeDesc> sourceTypeDescOpt = lookupService.lookup(sourceTypeRef);
        if(!sourceTypeDescOpt.isPresent()) {
            throw TypeCheckException.unknownType(sourceType, "Data Type");
        }
        if(sourceType.equals(targetType)) {
            /*
             * No type coercion is needed:
             */
            return sameType(sourceType);
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
        return typeCast(sourceType, targetType, sourceSemType);
    }


    static ITypeCoercion primType(String constant) {
        PrimitiveType primT = FMCompositionParser.primitiveTypeFor(constant);
        ITypeCoercion tc = de.upb.sede.composition.TypeCoercion.builder()
            .constant(constant)
            .sourceType(primT.name())
            .resultType(primT.name())
            .build();
        assert tc.getSemanticType() == null;
        return tc;
    }

    static ITypeCoercion sameType(String type) {
        ITypeCoercion tc = de.upb.sede.composition.TypeCoercion.builder()
            .sourceType(type)
            .resultType(type)
            .build();
        assert tc.getConstant() == null;
        assert tc.getSemanticType() == null;
        return tc;
    }

    static ITypeCoercion typeCast(String source, String result, String semantic) {
        ITypeCoercion tc = de.upb.sede.composition.TypeCoercion.builder()
            .sourceType(source)
            .resultType(result)
            .semanticType(semantic)
            .build();
        assert tc.getConstant() == null;
        return tc;
    }
}
