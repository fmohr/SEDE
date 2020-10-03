package ai.services.composition.faa;

import ai.services.composition.typing.TCOutput;
import ai.services.composition.InstInput;
import ai.services.composition.InstructionIndexer;

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
