package de.upb.sede.composition;

public interface CompileStep<I, O> {

    void setInput(I input);

    O getOutput();

    void step();

    boolean isFinished();

    default void run() {
        while(!isFinished())
            step();
    }
}
