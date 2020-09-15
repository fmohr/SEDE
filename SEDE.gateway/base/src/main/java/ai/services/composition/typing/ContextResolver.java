package ai.services.composition.typing;

import ai.services.SDLLookupService;
import ai.services.composition.InstWiseCompileStep;
import ai.services.composition.InstOutputIterator;
import ai.services.composition.graphs.nodes.IInstructionNode;
import ai.services.composition.types.TypeClass;
import ai.services.exec.IServiceDesc;
import ai.services.IServiceRef;

import java.util.Optional;

public class ContextResolver extends InstWiseCompileStep<TCInput, TCOutput> {

    ContextResolver(InstOutputIterator<TCOutput> outputIterator) {
        super(outputIterator);
    }

    @Override
    public void stepInst() {
        IInstructionNode inst = getCurrentInstruction().getInstruction();
        TCOutput currentOut = getInstOutput();
        TCOutput.ContextInfo context = currentOut.getContext();
        TCOutput.FieldTC fieldTC = currentOut.getFieldTC();
        if(inst.getContextIsFieldFlag()) {
            /*
             * type check field
             */
            String contextFieldName = inst.getContext();
            TypeClass fieldType = fieldTC.getFieldType(contextFieldName);
            if(fieldType == null) {
                throw TypeCheckException.undefinedField(contextFieldName);
            } else if(!TypeUtil.isService(fieldType)) {
                throw TypeCheckException.unexpectedFieldType(contextFieldName, fieldType, "Service Instance", "Service methods can only be invoked with service instances as a context.");
            }
            context.setStatic(false);
            context.setServiceQualifier(TypeUtil.getServiceType(fieldType).getTypeQualifier());
        } else {
            context.setStatic(true);
            context.setServiceQualifier(inst.getContext());
        }
        SDLLookupService lookupService = getInput().getLookupService();
        String serviceQualifier = context.getServiceQualifier();
        /*
         * Lookup the service description of the context
         */
        IServiceRef contextServiceRef = IServiceRef.of(null, serviceQualifier);
        Optional<IServiceDesc> serviceContextOpt = lookupService.lookup(contextServiceRef);
        IServiceDesc service = serviceContextOpt
            .orElseThrow(() -> TypeCheckException.unknownType(serviceQualifier, "service"));
        IServiceRef fullServiceRef;
        fullServiceRef = IServiceRef.of(
            lookupService.lookupCollection(contextServiceRef)
                .orElseThrow(() -> new RuntimeException("Implementation error... No corresponding collection found"))
                .getQualifier(),
            serviceQualifier);

        context.setServiceRef(fullServiceRef);
        context.setServiceDesc(service);
    }

}
