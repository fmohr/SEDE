package de.upb.sede.composition.typing;

import de.upb.sede.IServiceCollectionDesc;
import de.upb.sede.IServiceRef;
import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.InstOutputIterator;
import de.upb.sede.composition.InstWiseCompileStep;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.exec.*;
import de.upb.sede.util.SDLUtil;
import de.upb.sede.util.Streams;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MethodResolver extends InstWiseCompileStep<TCInput, TCOutput> {

    MethodResolver(InstOutputIterator<TCOutput> outputIterator) {
        super(outputIterator);
    }

    @Override
    public void stepInst() {
        IInstructionNode inst = getCurrentInstruction().getInstruction();
        TCOutput.ContextInfo context = getInstOutput().getContext();
        TCOutput.MethodInfo methodInfo = getInstOutput().getMethodInfo();
        SDLLookupService lookupService = getInput().getLookupService();

        /*
         * Resolve the method
         */
        String methodQualifier = inst.getMethod();
        methodInfo.setMethodQualifier(methodQualifier);

        resolveMethod();

        String serviceContextQualifier = context.getServiceQualifier();
        boolean staticContext = context.isStatic();
        IMethodDesc methodDesc = methodInfo.getMethodDesc();

        /*
         * Allow static context iff the method explicitly allows it:
         */
        if(staticContext && !methodDesc.isContextFree()) {
            throw TypeCheckException.illegalStaticContext(serviceContextQualifier, methodQualifier);
        }
        if(!staticContext && methodDesc.isContextFree()) {
            throw TypeCheckException.illegalNonStaticContext(serviceContextQualifier, methodQualifier);
        }
        assert methodDesc.getInputs().size() == inst.getParameterFields().size();
    }

    private void resolveMethod() {
        TCOutput.ContextInfo context = getInstOutput().getContext();

        boolean resolved = resolveMethodFrom(context.getServiceRef(), context.getServiceDesc());
        if(resolved) {
            return;
        }
        // try parents:
        List<IServiceDesc> parents = SDLUtil.c3Linearization(getInput().getLookupService(),
            context.getServiceRef());
        parents.remove(0);
        boolean methodResolved = false;
        for(IServiceDesc parent : parents) {
            Optional<IServiceCollectionDesc> optCollection = getInput().getLookupService().lookupCollection(IServiceRef.of(null, parent.getQualifier()));
            if(!optCollection.isPresent()) {
                throw TypeCheckException.unknownCollectionOfParent(context.getServiceQualifier(), parent.getQualifier());
            }
            methodResolved = resolveMethodFrom(IServiceRef.of(optCollection.get().getQualifier(), parent.getQualifier()), parent);
            if(methodResolved) {
                break;
            }
        }
        if(!methodResolved) {
            // No matching found:
            throw TypeCheckException.unknownMethodSignature(context.getServiceQualifier(),
                getInstOutput().getMethodInfo().getMethodQualifier(),
                getCurrentInstruction().getInstruction());
        }
    }

    private boolean resolveMethodFrom(IServiceRef serviceRef, IServiceDesc contextService) {
        String methodQualifier = getCurrentInstruction().getInstruction().getMethod();
        TCOutput.MethodInfo methodInfo = getInstOutput().getMethodInfo();

        SDLLookupService lookupService = getInput().getLookupService();

        IMethodRef methodRef = IMethodRef.of(serviceRef, methodQualifier);

        List<IMethodDesc> methodList = lookupService.lookup(methodRef);

        if(methodList.isEmpty()) {
            return false; // the given serivce doesn't define a method with the given name
        }

        Optional<IMethodDesc> optSignature = matchSignature(methodList, getCurrentInstruction().getInstruction());
        if(!optSignature.isPresent()) {
            return false; // the given service doesn't define a matching method signature
        }
        // method resolved!
        IMethodDesc method = optSignature.get();

        methodInfo.setMethodRef(methodRef);
        methodInfo.setMethodDesc(method);
        return true;
    }


    private Optional<IMethodDesc> matchSignature(List<IMethodDesc> methodList, IInstructionNode inst) {
        boolean instructionIsAssignment = inst.getFieldName() != null;
        Stream<IMethodDesc> matchingSignatures = methodList.stream()
            // method input must match in size:
            .filter(signature -> signature.getInputs().size() == inst.getParameterFields().size())
            // method must have at least one output if instruction is an assingment to a field:
            .filter(signature -> !instructionIsAssignment || !signature.getOutputs().isEmpty());
        // Only a single method has to match:
        Optional<IMethodDesc> matchingSignature = Streams.pickOneOrNone(matchingSignatures);

        return matchingSignature;
    }

}
