package de.upb.sede.composition.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import de.upb.sede.config.ClassesConfig;

/**
 * A directed, acyclic graph to represent a composition.
 * Each node contains the invocation information of one global procedure.
 * A edge between two nodes A -> B  denotes that B is dependent on A. This means that in order to execute A, B needs to be executed first.
 * 
 * This class is only a data-structure that holds nodes and edges. It shall not change any contained node and edge.
 * This way nodes can be in multiple graphs.
 * 
 */
public class Graph {
	/* node set */
	private final Collection<BaseNode> nodes;
	/* edge set */
	private final Collection<Edge> edges;

	public Graph() {
		this.nodes = new ArrayList<>();
		this.edges = new HashSet<>();
	}

	/**
	 * Returns an unmodifiable view on the node-set.
	 */
	Collection<BaseNode> getNodes() {
		return Collections.unmodifiableCollection(nodes);
	}

	/**
	 * Returns an unmodifiable view on the edge-set.
	 */
	Collection<Edge> getEdges() {
		return Collections.unmodifiableCollection(edges);
	}

	/**
	 * Alters this graph by adding the given node.
	 */
	public void addNode(BaseNode newNode) {
		nodes.add(newNode);
	}

	/**
	 * Alters this graph by adding the given edge.
	 * 
	 */
	private void addEdge(Edge newEdge) {
		if (newEdge.getFrom().equals(newEdge.getTo())) {
			return;
		}
		edges.add(newEdge);
	}

	/**
	 * Alters this graph by adding an edge from to to.
	 */
	public void connectNodes(BaseNode from, BaseNode to) {
		if (from.equals(to)) {
			return;
		}
		Edge newEdge = new Edge(from, to);
		addEdge(newEdge);
	}

	/**
	 * Returns true if this graph contains the given node.
	 */
	boolean contains(BaseNode node) {
		return nodes.contains(node);
	}

	/**
	 * Returns true if this graph connects 'from' to 'to'.
	 */
	boolean containsEdge(BaseNode from, BaseNode to) {
		return containsEdge(new Edge(from, to));
	}

	/**
	 * Returns true the edge is present in this graph.
	 */
	private boolean containsEdge(Edge edge) {
		return edges.contains(edge);
	}

	/**
	 * Removes the given node and its the edges from the graph.
	 */
	private void removeNode(BaseNode nodeToRemove) {
		if (nodeToRemove == null) {
			return;
		}
		/* remove all edges which contain the nodeToRemove. */
		edges.removeIf(edge -> edge.contains(nodeToRemove));
		/* remove the node itself. */
		nodes.remove(nodeToRemove);
	}


	



	

}