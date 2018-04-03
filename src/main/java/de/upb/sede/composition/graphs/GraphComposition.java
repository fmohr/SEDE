package de.upb.sede.composition.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.upb.sede.config.ClassesConfig;

/**
 * Composition in graph notation.
 */
public class GraphComposition {
	/**
	 * The order of the nodes matters!
	 * The first n nodes represent the n instructions from fmcomposition in the defined chronological order.
	 */
    private final List<BaseNode> nodes;
    private final Set<Edge> edges;

    public GraphComposition(){
        this.nodes = new ArrayList<>();
        this.edges = new HashSet<>();
    }


	public List<BaseNode> getNodes() {
		return Collections.unmodifiableList(nodes);
	}
    
    /**
     * Alters this graph by adding the given node.
     */
    public void addNode(BaseNode newNode){
        nodes.add(newNode);
    }
    

    /**
     * Alters this graph by adding the given edge.
     * 
     */
    private void addEdge(Edge newEdge) {
    		if(newEdge.getFrom().equals(newEdge.getTo())) {
    			return;
    		}
        edges.add(newEdge);
    }

    /**
     * Alters this graph by adding an edge from to to.
     */
    public void connectNodes(BaseNode from, BaseNode to) {
    		if(from.equals(to)) {
    			return;
    		}
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
        /* remove all edges which contain the nodeToRemove. */
        edges.removeIf(edge -> edge.contains(nodeToRemove));
        /* remove the node itself. */
        nodes.remove(nodeToRemove);
    }

    
    /**
     * Iterates all nodes and returns every node that changes the state for the given fieldname.
     * Returns an empty list if there is no node in this graph that produces the given fieldname.
     */
    final List<BaseNode> getChangers(String fieldname, ClassesConfig configuration) {
    		List<BaseNode> changers = new ArrayList<>();
    		for(BaseNode node : nodes) {
    			if(node.changesState(fieldname, configuration)) {
    				changers.add(node);
    			}
    		}
    		return changers;
    }


	


}