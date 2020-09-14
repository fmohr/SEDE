package de.upb.sede.composition;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class ChainedIWCompileStep<I extends InstInput, O> extends BlockWiseCompileStep<I, InstOutputIterator<O>>{

    private List<InstWiseCompileStep<I,O>> chainedSteps;

    public ChainedIWCompileStep(List<InstWiseCompileStep<I, O>> chainedSteps) {
        this.chainedSteps = chainedSteps;
    }

    public ChainedIWCompileStep(InstWiseCompileStep<I, O> instWiseCompileStep) {
        this(Collections.singletonList(instWiseCompileStep));
    }

    public ChainedIWCompileStep() {
        // the initializeSteps method needs to be overridden.
    }

    protected List<InstWiseCompileStep<I,O>> initializeSteps() {
        throw new IllegalStateException("The chained steps aren't initialized.");
    }

    private List<InstWiseCompileStep<I,O>> getChainedSteps() {
        if(chainedSteps == null) {
            chainedSteps = initializeSteps();
            Objects.requireNonNull(chainedSteps);
            if(chainedSteps.isEmpty()) {
                throw new IllegalArgumentException("Cannot create empty chain.");
            }
        }
        return chainedSteps;
    }

    @Override
    protected void stepBlock() {
        boolean firstIteration = true;
        /*
         * Until all are finished!
         */
        while(!getChainedSteps().stream().allMatch(InstWiseCompileStep::isFinished)) {
            /*
             * Step the shared iterator forward.
             */
            getOutput().next();
            if(firstIteration) {
                /*
                 * First initialize the compilation
                 */
                getChainedSteps().forEach(InstWiseCompileStep::initializeCompilation);
                firstIteration = false;
            }
            /*
             * For each instruction step, the entries one time:
             */
            getChainedSteps().forEach(InstWiseCompileStep::stepInst);
        }
        getChainedSteps().forEach(InstWiseCompileStep::finishStep);
    }

}
