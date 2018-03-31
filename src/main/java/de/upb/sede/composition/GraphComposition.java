package de.upb.sede.composition;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Composition in graph notation.
 */
public class GraphComposition {
    private final Set<BaseNode> nodes;
    private final Set<Edge> edges;

    public GraphComposition(){
        this.nodes = new HashSet<>();
        this.edges = new HashSet<>();
    }

    /**
     * Alters this graph by adding the given node.
     */
    public void addNode(BaseNode newNode){
        nodes.add(newNode);
    }

    /**
     * Alters this graph by adding the given edge.
     */
    private void addEdge(Edge newEdge) {
        edges.add(newEdge);
    }

    /**
     * Alters this graph by adding an edge from to to.
     */
    public void connectNodes(BaseNode from, BaseNode to) {
        Edge newEdge = new Edge(from, to);
        addEdge(newEdge);
    }

    /**
     * Returns true if this graph contains the given node.
     */
    private boolean contains(BaseNode node){
        return nodes.contains(node);
    }

    /**
     * Returns true if this graph connects 'from' to 'to'.
     */
    private boolean containsEdge(BaseNode from, BaseNode to){
        return containsEdge(new Edge(from, to));
    }

    /**
     * Returns true the edge is present in this graph.
     */
    private boolean containsEdge(Edge edge){
        return edges.contains(edge);
    }

    /**
     * Removes the given node and its the edges from the graph.
     */
    private void removeNode(BaseNode nodeToRemove){
        if(nodeToRemove == null) {
            return;
        }
        /* remove all edges which contain the node that is to be removed. */
        edges.removeIf(edge -> edge.contains(nodeToRemove));
        /* remove the node itself. */
        nodes.remove(nodeToRemove);
    }



}