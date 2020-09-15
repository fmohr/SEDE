package ai.services.composition.choerography;

import ai.services.composition.BlockWiseCompileStep;
import ai.services.exec.IExecutorHandle;
import ai.services.util.MappedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExecutionParticipantCollector
    extends BlockWiseCompileStep<ExecutionParticipantCollector.EPCInput, ExecutionParticipantCollector.EPCOutput> {

    public ExecutionParticipantCollector() {
    }

    @Override
    protected EPCOutput initializeOutput() {
        return new EPCOutput();
    }

    @Override
    protected void stepBlock() {
        getOutput().participantMap.put(getInput().clientHandle.getQualifier(), getInput().clientHandle);
        if(getOutput().participants.size() != 1) {
            throw new RuntimeException("Mapped List doesnt work.");
        }
        for (List<IExecutorHandle> value : getInput().candidates.values()) {
            value.forEach(executorHandle ->
                getOutput().participantMap.putIfAbsent(executorHandle.getQualifier(), executorHandle));
        }

    }

    static class EPCInput {

        private final Map<Long, List<IExecutorHandle>> candidates;

        private final IExecutorHandle clientHandle;

        EPCInput(Map<Long, List<IExecutorHandle>> candidates, IExecutorHandle clientHandle) {
            this.candidates = candidates;
            this.clientHandle = clientHandle;
        }
    }

    static class EPCOutput {

        private final List<IExecutorHandle> participants = new ArrayList<>();

        private final Map<String, IExecutorHandle> participantMap = new MappedList<>(participants, IExecutorHandle::getQualifier);

        public List<IExecutorHandle> getParticipants() {
            return participants;
        }
    }
}


