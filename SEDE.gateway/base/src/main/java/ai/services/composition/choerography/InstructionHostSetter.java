package ai.services.composition.choerography;

import ai.services.composition.IndexedInstruction;
import ai.services.composition.graphs.nodes.InstructionNode;
import ai.services.composition.BlockWiseCompileStep;
import ai.services.composition.IIndexedInstruction;
import ai.services.composition.InstructionIndexer;
import ai.services.exec.IExecutorHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InstructionHostSetter extends BlockWiseCompileStep<InstructionHostSetter.IHSInput,
    InstructionHostSetter.IHSOutput> {


    @Override
    protected IHSOutput initializeOutput() {
        return new IHSOutput();
    }

    @Override
    protected void stepBlock() {
        getOutput().instructions.clear();
        for (IIndexedInstruction former : getInput().indexer) {
            IIndexedInstruction inst = IndexedInstruction.builder()
                .instruction(InstructionNode.builder()
                    .from(former.getInstruction())
                    .hostExecutor(getInput().instExecutorMap.get(former.getIndex()).getQualifier())
                    .build())
                .build();
            getOutput().instructions.add(inst);
        }
    }

    static class IHSInput {

        private InstructionIndexer indexer;

        private Map<Long, IExecutorHandle> instExecutorMap;

        public IHSInput(InstructionIndexer indexer, Map<Long, IExecutorHandle> instExecutorMap) {
            this.indexer = indexer;
            this.instExecutorMap = instExecutorMap;
        }
    }

    static class IHSOutput {

        private final List<IIndexedInstruction> instructions = new ArrayList<>();


        public List<IIndexedInstruction> getInstructions() {
            return instructions;
        }
    }
}
