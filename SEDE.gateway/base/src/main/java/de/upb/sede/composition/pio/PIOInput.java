package de.upb.sede.composition.pio;

import de.upb.sede.composition.InstructionIndexer;

public class PIOInput {

    private final InstructionIndexer instructions;

    public PIOInput(InstructionIndexer instructions) {
        this.instructions = instructions;
    }

    public InstructionIndexer getInstructions() {
        return instructions;
    }
}
