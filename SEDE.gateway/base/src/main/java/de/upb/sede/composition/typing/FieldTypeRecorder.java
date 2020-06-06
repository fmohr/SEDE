package de.upb.sede.composition.typing;

import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.InstWiseCompileStep;
import de.upb.sede.composition.InstOutputIterator;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.types.*;
import de.upb.sede.core.PrimitiveType;
import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IMethodParameterDesc;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.IServiceRef;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;

import java.util.Optional;


/**
 * Fills in the type journal.
 */
public class FieldTypeRecorder extends InstWiseCompileStep<TCInput, TCOutput> {

    FieldTypeRecorder(InstOutputIterator<TCOutput> outputIterator) {
        super(outputIterator);
    }

    @Override
    protected void initializeCompilation() {
        /*
         * The initial Context may be defined and is not empty.
         * Then inject the injected field types into it
         */
        if(getInput().getInitialTypeContext() != null && !getInput().getInitialTypeContext().isEmpty()) {
            getInstOutput().getFieldTC().setInitialContext(getInput().getInitialTypeContext());
        }

    }

    @Override
    public void stepInst() {
        IInstructionNode inst = getCurrentInstruction().getInstruction();
        if(!inst.isAssignment()) {
            return;
        }
        /*
         * Instruction assigns to a field.
         * Record the resulting type
         */
        IMethodDesc methodDesc = getInstOutput().getMethodInfo().getMethodDesc();
        String methodQualifier = getInstOutput().getMethodInfo().getMethodQualifier();
        String assignedField = inst.getFieldName();
        if(methodDesc.getOutputs().isEmpty()) {
            throw TypeCheckException.methodNoReturnValue(methodQualifier, assignedField);
        }
        IMethodParameterDesc methodOutput = methodDesc.getOutputs().get(0);
        String returnType = methodOutput.getType();
        TypeClass fieldType;
        try {
            fieldType = getTypeClassOf(getInput().getLookupService(), returnType);
        } catch (TypeCheckException e) {
            throw new TypeCheckException(
                String.format("Error classifying return type of method %s::%s",
                    getInstOutput().getContext().getServiceQualifier(), methodQualifier ),
                e);
        }
        getInstOutput().getFieldTC().setFieldType(assignedField, fieldType);
    }


    private static TypeClass getTypeClassOf(SDLLookupService lookupService, String returnType) {
        /*
         * Assume type is a primitive qualifier, i.e. "Number", "String", "Bool"
         */
        Optional<PrimitiveType> primTypeOpt = PrimitiveType.insensitiveValueOf(returnType);
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
        IServiceRef serviceRef = IServiceRef.of(null, returnType);
        Optional<IServiceDesc> serviceDescOpt = lookupService.lookup(serviceRef);
        if(serviceDescOpt.isPresent()) {
            IServiceInstanceType serviceInstanceType = ServiceInstanceType.builder()
                .qualifier(returnType)
                .build();
            return serviceInstanceType;
        }
        /*
         * The last case remaining is a data value type.
         */
        IDataTypeRef dataTypeRef = IDataTypeRef.of(returnType);
        Optional<IDataTypeDesc> dataTypeOpt = lookupService.lookup(dataTypeRef);
        if(dataTypeOpt.isPresent()) {
            IDataValueType dataValueType = DataValueType.builder()
                .qualifier(returnType)
                .build();
            return dataValueType;
        }

        /*
         * The type qualifier was not recognized..
         */
        throw TypeCheckException.unknownType(returnType, "type");
    }

}
