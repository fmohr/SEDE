package ai.services.composition.pio;

import ai.services.composition.BlockWiseCompileStep;
import ai.services.composition.IIndexedInstruction;

import java.util.List;

public class ProgramInstructionOrderer extends BlockWiseCompileStep<PIOInput, PIOOutput> {

//    @Override
//    public PIOOutput step(PIOInput input) {
//        PIOOutput output = new PIOOutput();
//        List<Long> program = output.getProgramOrder();
//        for (IIndexedInstruction indexedInst : input.getInstructions()) {
//            // For now the conservative and oblivious implementation is program order.
//            // TODO implement a more efficient ordering
//            program.add(indexedInst.getIndex());
//        }
//        return output;
//    }

    @Override
    protected PIOOutput initializeOutput() {
        return new PIOOutput();
    }

    @Override
    protected void stepBlock() {
        List<Long> program = getOutput().getProgramOrder();
        for (IIndexedInstruction indexedInst : getInput().getInstructions()) {
            // For now the conservative and oblivious implementation is program order.
            // TODO implement a more efficient ordering
            program.add(indexedInst.getIndex());
        }
    }
}
