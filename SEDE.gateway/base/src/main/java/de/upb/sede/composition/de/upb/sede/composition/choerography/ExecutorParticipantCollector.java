package de.upb.sede.composition.de.upb.sede.composition.choerography;

import de.upb.sede.composition.BlockWiseCompileStep;
import de.upb.sede.exec.IExecutorHandle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecutorParticipantCollector
    extends BlockWiseCompileStep<ExecutorParticipantCollector.EPCInput, ExecutorParticipantCollector.EPCOutput> {

    public ExecutorParticipantCollector() {
    }

    @Override
    protected EPCOutput initializeOutput() {
        return new EPCOutput();
    }

    @Override
    protected void stepBlock() {
        for (List<IExecutorHandle> value : getInput().candidates.values()) {
            value.forEach(executorHandle ->
                getOutput().participants.put(executorHandle.getQualifier(), executorHandle));
        }
    }

    static class EPCInput {

        private final Map<Long, List<IExecutorHandle>> candidates;

        EPCInput(Map<Long, List<IExecutorHandle>> candidates) {
            this.candidates = candidates;
        }
    }

    static class EPCOutput {

        private final Map<String, IExecutorHandle> participants = new HashMap<>();

        public Map<String, IExecutorHandle> getParticipants() {
            return participants;
        }

    }
}


