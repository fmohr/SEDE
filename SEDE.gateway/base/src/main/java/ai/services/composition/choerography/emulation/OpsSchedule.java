package de.upb.sede.composition.choerography.emulation;

import de.upb.sede.composition.IIndexedInstruction;
import de.upb.sede.composition.InstructionIndexer;
import de.upb.sede.composition.orchestration.scheduled.ScheduledOperation;

import java.util.*;

public class OpsSchedule {
    private final Map<Long, InstOperations> instOps = new HashMap<>();
    private final List<ScheduledOperation> finalOps = new ArrayList<>();
    public OpsSchedule(InstructionIndexer indexer) {
        for (IIndexedInstruction inst : indexer) {
            instOps.put(inst.getIndex(), new InstOperations(inst));
        }
    }
    public InstOperations getInstOps(Long index) {
        return Objects.requireNonNull(instOps.get(index));
    }
    public List<ScheduledOperation> getFinalOps() {
        return finalOps;
    }
    public static class InstOperations {

        private final IIndexedInstruction instruction;

        private final List<ScheduledOperation> preInstOps = new ArrayList<>();

        private final List<ScheduledOperation> postInstOps = new ArrayList<>();

        public InstOperations(IIndexedInstruction instruction) {
            this.instruction = instruction;
        }

        public void addPreOp(ScheduledOperation object) {
            preInstOps.add(object);
        }

        public void addPostOp(ScheduledOperation op) {
            postInstOps.add(op);
        }

        public List<ScheduledOperation> getPreInstOps() {
            return preInstOps;
        }

        public List<ScheduledOperation> getPostInstOps() {
            return postInstOps;
        }
    }
}
