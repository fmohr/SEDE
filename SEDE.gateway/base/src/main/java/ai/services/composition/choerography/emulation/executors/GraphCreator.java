package ai.services.composition.choerography.emulation.executors;

import ai.services.composition.graphs.nodes.BaseNode;

import java.util.Arrays;
import java.util.List;

interface GraphCreator {

    default void addNodes(BaseNode... nodes) {
        addNodes(Arrays.asList(nodes));
    }

    void addNodes(List<BaseNode> nodes);

    default void addEdges(ExecutionGraph.GraphEdge... edges) {
        addEdges(Arrays.asList(edges));
    }

    void addEdges(List<ExecutionGraph.GraphEdge> edges);

    void addNodes(List<BaseNode> nodes, List<ExecutionGraph.GraphEdge> edges);

    default void consumeFields(List<String> toBeConsumed, BaseNode... consumers) {
        consumeFields(toBeConsumed, Arrays.asList(consumers));
    }

    void consumeFields(List<String> toBeConsumed, List<BaseNode> consumers);

    default void produceFields(List<String> toBeProduced, BaseNode... producers) {
        produceFields(toBeProduced, Arrays.asList(producers));
    }

    void produceFields(List<String> toBeProduced, List<BaseNode> producers);

}
