package de.upb.sede.composition.faa;

import de.upb.sede.composition.InstInput;
import de.upb.sede.composition.InstructionIndexer;
import de.upb.sede.composition.typing.TCOutput;
import de.upb.sede.composition.typing.TypeJournal;

import java.util.Map;

public class IFACInput implements InstInput {

    private final Map<Long, TCOutput> tcOutput;

    private final InstructionIndexer instructions;

    public IFACInput(Map<Long, TCOutput> tcOutput, InstructionIndexer instructions) {
        this.tcOutput = tcOutput;
        this.instructions = instructions;
    }

    public InstructionIndexer getInstructions() {
        return instructions;
    }

    public Map<Long, TCOutput> getTcOutput() {
        return tcOutput;
    }


}
