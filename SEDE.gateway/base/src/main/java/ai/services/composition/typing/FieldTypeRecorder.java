package ai.services.composition.typing;

import ai.services.composition.types.*;
import ai.services.SDLLookupService;
import ai.services.composition.InstWiseCompileStep;
import ai.services.composition.InstOutputIterator;
import ai.services.composition.graphs.nodes.IInstructionNode;
import ai.services.core.PrimitiveType;
import ai.services.exec.IMethodDesc;
import ai.services.exec.IMethodParameterDesc;
import ai.services.exec.IServiceDesc;
import ai.services.IServiceRef;
import ai.services.types.IDataTypeDesc;
import ai.services.types.IDataTypeRef;

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
                .typeQualifier(returnType)
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
                .typeQualifier(returnType)
                .build();
            return dataValueType;
        }

        /*
         * The type qualifier was not recognized..
         */
        throw TypeCheckException.unknownType(returnType, "type");
    }

}
