package ai.services.composition.choerography.emulation.executors;

import ai.services.composition.graphs.nodes.CompositionGraph;
import ai.services.composition.BlockWiseCompileStep;
import ai.services.composition.graphs.nodes.ICompositionGraph;

import java.util.ArrayList;
import java.util.List;

public class ChoreographyFinalizer extends  BlockWiseCompileStep<ChoreographyFinalizer.CFInput, ChoreographyFinalizer.CFOutput> {

    @Override
    protected CFOutput initializeOutput() {
        return new CFOutput();
    }

    @Override
    protected void stepBlock() {
        for (EmExecutor executor : getInput().getExecutors()) {
            ICompositionGraph compositionGraph = extractGraph(executor);
            getOutput().getGraph().add(compositionGraph);
        }
    }

    private ICompositionGraph extractGraph(EmExecutor executor) {
        if (!(executor instanceof GraphCreatingExecutor)) {
            throw new IllegalStateException("Executor Emulation " + executor.getClass() + " is not subtype of GraphCreatingExecutor.");
        }
        CompositionGraph.Builder builder = ((GraphCreatingExecutor) executor).getGraph().extractGraph()
            .executorHandle(executor.getExecutorHandle());
        if(getInput().getExecutors().getClientExecutor().getExecutorHandle().getQualifier()
            .equals(executor.getExecutorHandle().getQualifier())) {
            builder.isClient(true);
        }
        return builder.build();
    }

    public static class CFInput {

        private final ExecutionParticipants executors;

        public CFInput(ExecutionParticipants executors) {
            this.executors = executors;
        }

        public ExecutionParticipants getExecutors() {
            return executors;
        }
    }

    public static class CFOutput {
        private List<ICompositionGraph> graph = new ArrayList<>();

        public List<ICompositionGraph> getGraph() {
            return graph;
        }
    }

}
