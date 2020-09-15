package ai.services.composition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class InstWiseCompileStep<T extends InstInput,U> implements CompileStep<T, InstOutputIterator<U>> {

    private static final Logger logger = LoggerFactory.getLogger(InstructionIndexer.class);

    private T input;

    private InstOutputIterator<U> outputIterator;

    private boolean firstInstructionFlag = true;

    private boolean finishedCurrentInstructionFlag = false;

    protected InstWiseCompileStep(InstructionIndexer instructions) {
        outputIterator = new InstOutputIterator<U>(this::initializeOutput, instructions);
    }

    protected InstWiseCompileStep() {

    }

    protected InstWiseCompileStep(InstOutputIterator<U> outputIterator) {
        this.outputIterator = outputIterator;
    }

    protected IIndexedInstruction getCurrentInstruction() {
        return getOutput().getCurrentInstruction();
    }

    public void setInput(T t) {
        this.input = t;
    }

    protected T getInput() {
        if(input == null) {
            throw new IllegalStateException("Input has not been set yet");
        }
        return input;
    }

    protected U initializeOutput() {
        throw new IllegalStateException("Method implementation was not done.");
    }

    public InstOutputIterator<U> getOutput() {
        if(outputIterator == null) {
            InstructionIndexer instructions = getInput().getInstructions();
            outputIterator = new InstOutputIterator<U>(this::initializeOutput, instructions);
        }
        return outputIterator;
    }

    protected U getInstOutput() {
        return getOutput().getCurrent();
    }


    public boolean isFinished() {
        return !getOutput().hasNext();
    }

    protected void initializeCompilation() {
    }

    @Override
    public final void step() {
        if(isFinished()) {
            return;
        }
        if(firstInstructionFlag) {
            initializeCompilation();
            firstInstructionFlag = false;
        } else {
            if(getOutput().hasNext()) {
                getOutput().next();
                stepInst();
            }
            finishStep();
        }
    }

    public abstract void stepInst();

    public void finishStep() {

    }

}
