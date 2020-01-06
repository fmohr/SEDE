package de.upb.sede.composition.typing;

import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.InstOutputIterator;
import de.upb.sede.composition.InstWiseCompileStep;
import de.upb.sede.composition.InstructionIndexer;
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
        ISignatureDesc signature = methodInfo.getSignatureDesc();

        /*
         * Allow static context iff the method explicitly allows it:
         */
        if(staticContext && !signature.isContextFree()) {
            throw TypeCheckException.illegalStaticContext(serviceContextQualifier, methodQualifier);
        }
        if(!staticContext && signature.isContextFree()) {
            throw TypeCheckException.illegalNonStaticContext(serviceContextQualifier, methodQualifier);
        }
        assert signature.getInputs().size() == inst.getParameterFields().size();
    }

    private void resolveMethod() {
        TCOutput.ContextInfo context = getInstOutput().getContext();

        boolean resolved = resolveMethodFrom(context.getServiceDesc());
        if(resolved) {
            return;
        }
        // try parents:
        List<IServiceDesc> parents = SDLUtil.c3Linearization(getInput().getLookupService(),
            context.getServiceRef());
        parents.remove(0);
        boolean methodResolved = false;
        for(IServiceDesc parent : parents) {
            methodResolved = resolveMethodFrom(parent);
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

    private boolean resolveMethodFrom(IServiceDesc contextService) {
        String methodQualifier = getCurrentInstruction().getInstruction().getMethod();
        TCOutput.MethodInfo methodInfo = getInstOutput().getMethodInfo();

        SDLLookupService lookupService = getInput().getLookupService();

        IMethodRef methodRef = IMethodRef.of(
            IServiceRef.of(null, contextService.getQualifier()),
            methodQualifier);

        Optional<IMethodDesc> optMethod = lookupService.lookup(methodRef);

        if(!optMethod.isPresent()) {
            return false; // the given serivce doesn't define a method with the given name
        }

        IMethodDesc method = optMethod.get();
        Optional<ISignatureDesc> optSignature = matchSignature(method, getCurrentInstruction().getInstruction());
        if(!optSignature.isPresent()) {
            return false; // the given service doesn't define a matching method signature
        }
        // method resolved!
        ISignatureDesc signature = optSignature.get();

        methodInfo.setMethodRef(methodRef);
        methodInfo.setSignatureDesc(signature);
        methodInfo.setMethodDesc(method);
        return true;
    }


    private Optional<ISignatureDesc> matchSignature(IMethodDesc method, IInstructionNode inst) {
        List<ISignatureDesc> signatures = method.getSignatures();
        boolean instructionIsAssignment = inst.getFieldName() != null;
        Stream<ISignatureDesc> matchingSignatures = signatures.stream()
            // method input must match in size:
            .filter(signature -> signature.getInputs().size() == inst.getParameterFields().size())
            // method must have at least one output if instruction is an assingment to a field:
            .filter(signature -> !instructionIsAssignment || !signature.getOutputs().isEmpty());
        // Only a single method has to match:
        Optional<ISignatureDesc> matchingSignature = Streams.pickOneOrNone(matchingSignatures);

        return matchingSignature;
    }

}
