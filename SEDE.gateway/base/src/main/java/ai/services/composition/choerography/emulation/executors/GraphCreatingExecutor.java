package ai.services.composition.choerography.emulation.executors;

public interface GraphCreatingExecutor extends EmExecutor, GraphCreator {

    ExecutionGraph getGraph();

}
