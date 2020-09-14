package de.upb.sede.composition.choerography.emulation.executors;

import de.upb.sede.exec.IExecutorHandle;
import de.upb.sede.util.MappedList;

import java.util.*;

public class ExecutionParticipants implements Iterable<EmExecutor> {

    private final List<EmExecutor> executors;

    private final Map<String, EmExecutor> executorMap;

    private final EmExecutor clientExecutor;

    public ExecutionParticipants(List<EmExecutor> executorList, IExecutorHandle clientExecutorH) {
        this.executors = Collections.unmodifiableList(new ArrayList<>(executorList));

        this.executorMap = new MappedList<>(executors, executor -> executor.getExecutorHandle().getQualifier());

        this.clientExecutor = executorMap.get(clientExecutorH.getQualifier());
        Objects.requireNonNull(this.clientExecutor);
    }

    public EmExecutor getExecutor(String executorId) {
        return executorMap.get(executorId);
    }

    public EmExecutor getClientExecutor() {
        return clientExecutor;
    }

    public int size() {
        return executors.size();
    }

    @Override
    public Iterator<EmExecutor> iterator() {
        return executors.iterator();
    }

}
