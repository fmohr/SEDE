package de.upb.sede.composition.faa;

import de.upb.sede.composition.IMethodCognition;
import de.upb.sede.composition.InstructionIndexer;
import de.upb.sede.composition.typing.InstIndexMap;
import de.upb.sede.composition.typing.TCOutput;
import de.upb.sede.composition.typing.TypeJournal;

public class FAAInput {


    private final TCOutput tcOutput;

    private final InstructionIndexer instructions;

    public FAAInput(TCOutput tcOutput, InstructionIndexer instructions) {
        this.tcOutput = tcOutput;
        this.instructions = instructions;
    }

    public InstructionIndexer getInstructions() {
        return instructions;
    }

    public InstIndexMap<IMethodCognition> getInstMethodCognition() {
        return tcOutput.getMethodCognitionMap();
    }

    public TypeJournal getTypeJournal() {
        return tcOutput.getJournal();
    }

}
