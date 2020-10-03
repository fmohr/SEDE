package ai.services.composition.pio;

import ai.services.composition.InstructionIndexer;

public class PIOInput {

    private final InstructionIndexer instructions;

    public PIOInput(InstructionIndexer instructions) {
        this.instructions = instructions;
    }

    public InstructionIndexer getInstructions() {
        return instructions;
    }
}
