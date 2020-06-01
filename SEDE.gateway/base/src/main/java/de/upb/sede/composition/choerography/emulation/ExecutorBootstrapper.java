package de.upb.sede.composition.choerography.emulation;

import de.upb.sede.composition.BlockWiseCompileStep;
import de.upb.sede.composition.choerography.emulation.executors.ExecutorFactory;
import de.upb.sede.exec.IExecutorHandle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecutorBootstrapper
    extends BlockWiseCompileStep
    <ExecutorBootstrapper.EBInput, ExecutorBootstrapper.EBOutput> {

    @Override
    protected EBOutput initializeOutput() {
        return new EBOutput();
    }

    @Override
    protected void stepBlock() {
        bootstrapExecutor();
        getOutput().clientExecutor = getInput().client;
    }
    private void bootstrapExecutor() {
        for (IExecutorHandle participant : getInput().participants) {
            Emulation.Executor executor = getInput().executorFactory.createExecutor(participant);
            getOutput().put(participant, executor);
        }
    }

    public static class EBInput {

        private final ExecutorFactory executorFactory;

        private final List<IExecutorHandle> participants;

        private final IExecutorHandle client;

        public EBInput(ExecutorFactory executorFactory, List<IExecutorHandle> participants, IExecutorHandle client) {
            this.executorFactory = executorFactory;
            this.participants = participants;
            this.client = client;
        }
    }

    static class EBOutput {

        private final Map<String, IExecutorHandle> handles = new HashMap<>();

        private final Map<String, Emulation.Executor> executors = new HashMap<>();

        private IExecutorHandle clientExecutor;

        private void put(IExecutorHandle handle, Emulation.Executor executor) {
            handles.put(handle.getQualifier(), handle);
            executors.put(handle.getQualifier(), executor);
        }

        public Emulation.ExecutorRegistry getFinalOutput() {
            return new Emulation.ExecutorRegistry(executors, handles, clientExecutor);
        }

    }

}
