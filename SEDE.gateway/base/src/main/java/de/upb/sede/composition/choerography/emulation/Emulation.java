package de.upb.sede.composition.choerography.emulation;

import de.upb.sede.composition.orchestration.EmulatedOp;
import de.upb.sede.exec.IExecutorHandle;
import de.upb.sede.util.MappedList;

import java.util.*;

public interface Emulation {

    class ExecutorRegistry implements Iterable<Executor> {

        private List<Executor> executors;

        private Map<String, Executor> executorMap;

        private Executor clientExecutor;

        private IExecutorHandle clientExecutorHandle;

        ExecutorRegistry(List<Executor> executorList, IExecutorHandle clientExecutorH) {
            this.executors = Collections.unmodifiableList(new ArrayList<>(executorList));
            this.clientExecutorHandle = clientExecutorH;

            this.executorMap = new MappedList<>(executors, executor -> executor.getExecutorHandle().getQualifier());

            this.clientExecutor = executorMap.get(clientExecutorH.getQualifier());
            Objects.requireNonNull(this.clientExecutor);
        }

        Executor getExecutor(String executorId) {
            return executorMap.get(executorId);
        }

        Executor getClientExecutor() {
            return clientExecutor;
        }

        public int size() {
            return executors.size();
        }

        @Override
        public Iterator<Executor> iterator() {
            return executors.iterator();
        }

    }

    interface Executor {

        void execute(EmulatedOp emulatedOp) throws EmulationException;

        IExecutorHandle getExecutorHandle();



    }



}
