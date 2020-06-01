package de.upb.sede.composition;

import java.util.Objects;

public abstract class BlockWiseCompileStep<I, O> implements CompileStep<I, O> {

    private I input;

    private O output;

    private boolean finishedFlag = false;

    protected void assertReadyState() {
        if(input == null) {
            throw new IllegalStateException("Input was not set.");
        }
    }

    @Override
    public final void setInput(I input) {
        if (this.input != null) {
            throw new IllegalStateException("Input was already set.");
        }
        this.input = Objects.requireNonNull(input, "Input is null");
    }

    protected final I getInput() {
        return input;
    }

    @Override
    public final O getOutput() {
        assertReadyState();
        if(output == null) {
            this.output = initializeOutput();
        }
        return output;
    }

    protected abstract O initializeOutput();

    @Override
    public final void step() {
        assertReadyState();
        stepBlock();
        finishedFlag = true;
    }

    @Override
    public final void stepToEnd() {
        step();
    }

    protected abstract void stepBlock();

    @Override
    public boolean isFinished() {
        return finishedFlag;
    }

}
