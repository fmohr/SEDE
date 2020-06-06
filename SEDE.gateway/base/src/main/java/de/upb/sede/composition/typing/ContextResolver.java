package de.upb.sede.composition.typing;

import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.InstWiseCompileStep;
import de.upb.sede.composition.InstOutputIterator;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.types.TypeClass;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.IServiceRef;

import java.util.Optional;

import static de.upb.sede.composition.typing.TypeUtil.getServiceType;
import static de.upb.sede.composition.typing.TypeUtil.isService;

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
            } else if(!isService(fieldType)) {
                throw TypeCheckException.unexpectedFieldType(contextFieldName, fieldType, "Service Instance", "Service methods can only be invoked with service instances as a context.");
            }
            context.setStatic(false);
            context.setServiceQualifier(getServiceType(fieldType).getQualifier());
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
