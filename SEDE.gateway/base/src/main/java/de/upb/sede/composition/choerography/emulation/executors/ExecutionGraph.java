package de.upb.sede.composition.choerography.emulation.executors;

import de.upb.sede.composition.graphs.nodes.BaseNode;

import java.util.*;

class ExecutionGraph {

    private final Set<GraphNode> nodes;

    private final Set<GraphEdge> edges;

    public ExecutionGraph() {
        nodes = new HashSet<>();
        edges = new HashSet<>();
    }

    public ExecutionGraph(HashSet<GraphNode> graphNodes, HashSet<GraphEdge> graphEdges) {
        this.nodes = graphNodes;
        this.edges = graphEdges;
    }

    public void addNode(BaseNode baseNode) {
        nodes.add(new GraphNode(baseNode));
    }

    public void addEdge(BaseNode n1, BaseNode n2) {
        addEdge(new GraphEdge(n1, n2));
    }

    public void addEdge(GraphEdge edge) {
        this.edges.add(edge);
    }

    public Iterable<BaseNode> getNodes() {
        return () -> nodes.stream().map(GraphNode::getNode).iterator();
    }

    public Collection<GraphEdge> getEdges() {
        return edges;
    }

    public boolean contains(BaseNode source) {
        return nodes.contains(source);
    }

    public GraphEdge edge(BaseNode n1, BaseNode n2) {
        return new GraphEdge(n1, n2);
    }

    public ExecutionGraph shallowClone() {
        return new ExecutionGraph(new HashSet<>(nodes), new HashSet<>(edges));
    }

    public void removeNode(BaseNode next) {
        nodes.remove(next);
    }

    public boolean containsEdge(BaseNode node, BaseNode otherNode) {
        return edges.contains(new GraphEdge(node, otherNode));
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    static class GraphNode {
        private final BaseNode node;

        private GraphNode(BaseNode node) {
            this.node = Objects.requireNonNull(node);
        }

        public BaseNode getNode() {
            return node;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null)
                return false;

            if(o instanceof BaseNode) {
                return node == o;
            }
            if(o instanceof GraphNode) {
                return node == ((GraphNode) o).node;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return node.hashCode();
        }
    }

    static class GraphEdge {

        private final GraphNode src, trg;

        GraphEdge(GraphNode n1, GraphNode n2) {
            this.src = Objects.requireNonNull(n1);
            this.trg = Objects.requireNonNull(n2);
        }

        public GraphEdge(BaseNode n1, BaseNode n2) {
            this.src = new GraphNode(n1);
            this.trg = new GraphNode(n2);
        }

        public GraphNode getFrom() {
            return src;
        }

        public GraphNode getTo() {
            return trg;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            GraphEdge graphEdge = (GraphEdge) o;

            if (!src.equals(graphEdge.src))
                return false;
            return trg.equals(graphEdge.trg);
        }

        @Override
        public int hashCode() {
            int result = src.hashCode();
            result = 31 * result + trg.hashCode();
            return result;
        }

        public boolean contains(BaseNode node) {
            return src.equals(node) || trg.equals(node);
        }

    }
}
