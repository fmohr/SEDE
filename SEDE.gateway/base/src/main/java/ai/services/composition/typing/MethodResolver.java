package ai.services.composition.typing;

import ai.services.IServiceCollectionDesc;
import ai.services.IServiceRef;
import ai.services.SDLLookupService;
import ai.services.composition.InstOutputIterator;
import ai.services.composition.InstWiseCompileStep;
import ai.services.composition.graphs.nodes.IInstructionNode;
import ai.services.exec.IMethodDesc;
import ai.services.exec.IMethodRef;
import ai.services.exec.IServiceDesc;
import ai.services.util.SDLUtil;

import java.util.List;
import java.util.Optional;

import static ai.services.util.SDLUtil.matchSignature;

public class MethodResolver extends InstWiseCompileStep<TCInput, TCOutput> {

    MethodResolver(InstOutputIterator<TCOutput> outputIterator) {
        super(outputIterator);
    }

    @Override
    public void stepInst() {
        IInstructionNode inst = getCurrentInstruction().getInstruction();
        TCOutput.ContextInfo context = getInstOutput().getContext();
        TCOutput.MethodInfo methodInfo = getInstOutput().getMethodInfo();

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




}
