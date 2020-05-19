package de.upb.sede.composition;

public abstract class LocalBestSolutionCompileStep<I, O> extends BlockWiseCompileStep<I, O> {

    boolean changedFlag = true;

    @Override
    protected final void stepBlock() {
        while(changedFlag) {
            changedFlag = false;
            iterate();
        }
    }

    private boolean checkFlagAndReset() {
        boolean iterationhangs =
    }

    protected abstract void iterate();

}
