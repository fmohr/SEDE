package ai.services.composition.typing;

import ai.services.composition.ChainedIWCompileStep;
import ai.services.composition.InstOutputIterator;
import ai.services.composition.InstWiseCompileStep;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TypeChecker extends ChainedIWCompileStep<TCInput, TCOutput> {


    @Override
    protected List<InstWiseCompileStep<TCInput, TCOutput>> initializeSteps() {
        // All compile steps share an output:
        InstOutputIterator<TCOutput> output = getOutput();
        ContextResolver contextResolver = new ContextResolver(output);
        MethodResolver methodResolver = new MethodResolver(output);
        FieldTypeRecorder typeChecker = new FieldTypeRecorder(output);
        ParamTypeCoercionResolver paramTypeCoercionResolver = new ParamTypeCoercionResolver(output);

        List<InstWiseCompileStep<TCInput, TCOutput>> steps = Arrays.asList(contextResolver, methodResolver, paramTypeCoercionResolver, typeChecker);
        try {
            steps.forEach(step -> step.setInput(getInput()));
        } catch(TypeCheckException ex) {
            throw new TypeCheckException(getOutput().getCurrentInstruction(), ex);
        }
        return steps;
    }

    @Override
    protected void stepBlock() {
        try {
            super.stepBlock();
        } catch(TypeCheckException ex) {
            throw new TypeCheckException(getOutput().getCurrentInstruction(), ex);
        }
    }

    @Override
    protected InstOutputIterator<TCOutput> initializeOutput() {
        return new InstOutputIterator<>(
            (Function<TCOutput, TCOutput>) TCOutput::new,
            getInput().getInstructions());
    }


}