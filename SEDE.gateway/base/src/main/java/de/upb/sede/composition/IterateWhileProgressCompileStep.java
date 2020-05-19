package de.upb.sede.composition;

public abstract class IterateWhileProgressCompileStep<I, O> extends BlockWiseCompileStep<I, O> {

    boolean progressed = true;

    @Override
    protected final void stepBlock() {
        while(checkProgressAndReset()) {
            iterateSolution();
        }
    }

    private boolean checkProgressAndReset() {
        boolean progressInIteration = progressed;
        progressed = false;
        return progressInIteration;
    }

    protected void setProgressed() {
        this.progressed = true;
    }

    protected abstract void iterateSolution();

}
