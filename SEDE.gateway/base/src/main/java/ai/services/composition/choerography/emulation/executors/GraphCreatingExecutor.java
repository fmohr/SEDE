package de.upb.sede.composition.choerography.emulation.executors;

public interface GraphCreatingExecutor extends EmExecutor, GraphCreator {

    ExecutionGraph getGraph();

}
