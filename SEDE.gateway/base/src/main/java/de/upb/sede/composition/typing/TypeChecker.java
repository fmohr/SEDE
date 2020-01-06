package de.upb.sede.composition.typing;

import de.upb.sede.composition.ChainedIWCompileStep;
import de.upb.sede.composition.InstOutputIterator;
import de.upb.sede.composition.InstWiseCompileStep;

import java.util.ArrayList;
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

        List<InstWiseCompileStep<TCInput, TCOutput>> steps = Arrays.asList(contextResolver, methodResolver, typeChecker, paramTypeCoercionResolver);
        steps.forEach(step -> step.setInput(getInput()));
        return steps;
    }

    @Override
    protected InstOutputIterator<TCOutput> initializeOutput() {
        return new InstOutputIterator<>(
            (Function<TCOutput, TCOutput>) TCOutput::new,
            getInput().getInstructions());
    }


}
