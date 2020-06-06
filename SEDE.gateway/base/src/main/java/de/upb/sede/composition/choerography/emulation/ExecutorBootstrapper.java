package de.upb.sede.composition.choerography.emulation;

import de.upb.sede.composition.BlockWiseCompileStep;
import de.upb.sede.composition.choerography.emulation.executors.EmExecutor;
import de.upb.sede.composition.choerography.emulation.executors.ExecutionParticipants;
import de.upb.sede.composition.choerography.emulation.executors.ExecutorFactory;
import de.upb.sede.exec.IExecutorHandle;

import java.util.ArrayList;
import java.util.List;

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
            EmExecutor executor = getInput().executorFactory.createExecutor(participant);
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

    public static class EBOutput {

        private final List<EmExecutor> executors = new ArrayList<>();

        private IExecutorHandle clientExecutor;

        private void put(IExecutorHandle handle, EmExecutor executor) {
            executors.add(executor);
        }

        public ExecutionParticipants getFinalOutput() {
            return new ExecutionParticipants(executors, clientExecutor);
        }

    }

}
