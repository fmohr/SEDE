package ai.services.composition.choerography.emulation.executors;

import ai.services.composition.choerography.emulation.EmulationException;
import ai.services.composition.graphs.nodes.BaseNode;
import ai.services.composition.orchestration.emulated.EmulatedOp;
import ai.services.exec.IExecutorHandle;

import java.util.*;

public class BaseExecutor implements GraphCreatingExecutor {

    private final IExecutorHandle handle;

    private final String executorId;

    private final ExecutionGraph graph = new ExecutionGraph();

    private final Map<String, FieldProducer> fieldProducer = new HashMap<>();

    public void addNodes(BaseNode... nodes) {
        if(nodes == null || nodes.length == 0) {
            throw new IllegalArgumentException("Empty nodes");
        }
        addNodes(Arrays.asList(nodes));
    }

    public void addNodes(List<BaseNode> nodes) {
        nodes.forEach(graph::addNode);
    }

    public void addEdges(ExecutionGraph.GraphEdge... edges) {
        if(edges == null || edges.length == 0) {
            throw new IllegalArgumentException("Empty edges");
        }
        addEdges(Arrays.asList(edges));
    }

    public void addEdges(List<ExecutionGraph.GraphEdge> edges) {
        edges.forEach(graph::addEdge);
    }

    public void addNodes(List<BaseNode> nodes, List<ExecutionGraph.GraphEdge> edges) {
        nodes.forEach(graph::addNode);
        edges.forEach(graph::addEdge);
    }

    public void consumeFields(List<String> toBeConsumed, BaseNode... consumers) {
        if(consumers == null || consumers.length == 0) {
            throw new IllegalArgumentException("empty consumers");
        }
        consumeFields(toBeConsumed, Arrays.asList(consumers));
    }

    public void consumeFields(List<String> toBeConsumed, List<BaseNode> consumers) {
        for (String fieldname : toBeConsumed) {
            FieldProducer fieldProducer = this.fieldProducer.get(fieldname);
            if(fieldProducer == null) {
                continue;
            }
            for (BaseNode producer : fieldProducer.getProducers()) {
                for (BaseNode consumer : consumers) {
                    ExecutionGraph.GraphEdge edge = new ExecutionGraph.GraphEdge(producer, consumer);
                    graph.addEdge(producer, consumer);
                }
            }
            this.fieldProducer.remove(fieldname);
        }
    }

    public void produceFields(List<String> toBeProduced, BaseNode... producers) {
        if(producers == null || producers.length == 0) {
            throw new IllegalArgumentException("empty producers");
        }
        produceFields(toBeProduced, Arrays.asList(producers));
    }

    public void produceFields(List<String> toBeProduced, List<BaseNode> producers) {
        Objects.requireNonNull(toBeProduced, "No fields are given to be produced");
        if(producers == null) {
            producers = Collections.emptyList();
        }
        for (String fieldname : toBeProduced) {
            fieldProducer.put(fieldname, new FieldProducer(producers, fieldname));
        }
    }

    public BaseExecutor(IExecutorHandle handle) {
        this.handle = handle;
        this.executorId = handle.getQualifier();
    }

    @Override
    public void execute(EmulatedOp emulatedOp) throws EmulationException {
        if(!emulatedOp.wasHandled()) {
            // The decorators should execute this operation.
            throw new EmulationException("Operation reached base executor without being handled:\n" + emulatedOp);
        }
    }

    @Override
    public IExecutorHandle getExecutorHandle() {
        return handle;
    }

    public ExecutionGraph getGraph() {
        return graph;
    }


    private static class FieldProducer {

        private List<BaseNode> producers;

        private String fieldname;

        public FieldProducer(List<BaseNode> producers, String fieldname) {
            this.producers = producers;
            this.fieldname = fieldname;
        }

        public List<BaseNode> getProducers() {
            return producers;
        }

        public String getFieldname() {
            return fieldname;
        }
    }
}
