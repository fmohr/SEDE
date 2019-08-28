package de.upb.sede.composition.graphs;

import java.util.*;

import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.util.Iterators;

/**
 * A directed, acyclic graph to represent a composition. Each node contains the
 * invocation information of one global procedure. A edge between two nodes A ->
 * B denotes that B is dependent on A. This means that in order to execute A, B
 * needs to be executed first.
 *
 * This class is only a data-structure that holds nodes and edges. It shall not
 * change any contained node and edge. This way nodes can be in multiple graphs.
 *
 */
public class CompositionGraph {
	/* node set */
	private final Set<BaseNode> nodes;
	/* edge set */
	private final Set<DependencyEdge> edges;

	public CompositionGraph() {
		this.nodes = new HashSet<>();
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
	Collection<DependencyEdge> getEdges() {
		return Collections.unmodifiableCollection(edges);
	}

	/**
	 * Alters this graph by adding the given node.
	 */
	public void addNode(BaseNode newNode) {
		nodes.add(Objects.requireNonNull(newNode));
	}

	/**
	 * Alters this graph. From the given graph adds all edges whose source and
	 * target nodes are also contained in this graph.
	 */
	public void addEdgesFromGraph(CompositionGraph graph) {
		graph.edges.stream().filter(e -> this.contains(e.getFrom()) && this.contains(e.getTo())).forEach(this::addEdge);
	}

	/**
	 * Alters this graph by adding the given edge.
	 *
	 */
	public void addEdge(DependencyEdge newEdge) {
		Objects.requireNonNull(newEdge);
		// method and this class ensures it wont be invoked with null objects.
		if (newEdge.getFrom().equals(newEdge.getTo())) {
			throw new RuntimeException("Cannot connect to the a node to itself.");
		}
		if (contains(newEdge.getFrom()) && contains(newEdge.getTo())) {
			edges.add(newEdge);
		} else {
			throw new RuntimeException("Cannot connect two nodes that arent in the graph.");
		}

	}

	/**
	 * Alters this graph by adding an edge from to to.
	 */
	public void connectNodes(BaseNode from, BaseNode to) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);
		if (from.equals(to)) {
			throw new RuntimeException("Cannot connect to the a node to itself.");
		}
		DependencyEdge newEdge = new DependencyEdge(from, to);
		addEdge(newEdge);
	}

	/**
	 * Alters this graph by adding the given set of nodes to this graph and connecting everyone of them
	 * with every node that has no incoming edge. <p>
	 *
	 * The execution of the resulting graph would need to process the given nodes first.
	 */
	public void executeFirst(Collection<BaseNode> nodes){
		/*
			Gather a list of nodes that are independent (no incoming edge):
		 */
		List<BaseNode> independentNodes = Iterators.TO_LIST(GraphTraversal.independentNodes(this).iterator());
		/*
			Make sure the given nodes are already in the graph:
		 */
		for(BaseNode node : nodes) {
			if(!this.contains(node)) {
				this.addNode(node);
			}
		}
		/*
			Add an edge from the given nodes to the independent ones:
		 */
		for(BaseNode independentNode : independentNodes) {
			for(BaseNode givenNode : nodes) {
				this.connectNodes(givenNode, independentNode);
			}
		}
	}

	/**
	 * Alters this graph by adding the given set of nodes to this graph and connecting everyone of them
	 * with every node that has no outgoing edge. <p>
	 *
	 * The execution of the resulting graph would need to process the given nodes last.
	 */
	public void executeLast(Collection<BaseNode> nodes){
		/*
			Gather a list of nodes that are last (no outgoing edge):
		 */
		List<BaseNode> lastNodes = Iterators.TO_LIST(GraphTraversal.lastNodes(this).iterator());
		/*
			Make sure the given nodes are already in the graph:
		 */
		for(BaseNode node : nodes) {
			if(!this.contains(node)) {
				this.addNode(node);
			}
		}
		/*
			Add an edge from the given nodes to the independent ones:
		 */
		for(BaseNode lastNode : lastNodes) {
			for(BaseNode givenNode : nodes) {
				this.connectNodes(lastNode, givenNode);
			}
		}
	}

	/**
	 * Returns true if this graph contains the given node.
	 */
	public boolean contains(BaseNode node) {
		return nodes.contains(node);
	}

	/**
	 * Returns true if this graph connects 'from' to 'to'.
	 */
	boolean containsEdge(BaseNode from, BaseNode to) {
		return containsEdge(new DependencyEdge(from, to));
	}

	/**
	 * Returns true if the given edge is present in this graph.
	 */
	private boolean containsEdge(DependencyEdge edge) {
		return edges.contains(edge);
	}

	public boolean containsEdges() {
		return !edges.isEmpty();
	}

	public boolean isEmpty() {
		return getNodes().isEmpty();
	}

	/**
	 * This method connects node1 to every target node of node2.
	 * Basically:
	 * <pre>
	 * for(BaseNode bn : GraphTraversal.targetingNodes(this, node2)) {
	 * 		connectNodes(node1, bn);
	 * }
	 * </pre>
	 */
	public void connectToItsTargets(BaseNode node1, BaseNode node2) {
		for(BaseNode bn : GraphTraversal.targetingNodes(this, node2)) {
			connectNodes(node1, bn);
		}
	}

	/**
	 * Removes the given node and its the edges from the graph.
	 */
	public void removeNode(BaseNode nodeToRemove) {
		if (nodeToRemove == null) {
			return;
		}
		/* remove all edges which contain the nodeToRemove. */
		edges.removeIf(edge -> edge.contains(nodeToRemove));
		/* remove the node itself. */
		nodes.remove(nodeToRemove);
	}

	/**
	 * Clones this graph by copying edge and node set to a new Graph Object. O(m+n)
	 * time complexity and O(1) space efficiency. (Doesn't clone the nodes so
	 * changing the state of nodes of the cloned graph will have effects on the
	 * original graph. This behavior is intentional so don't change it.)
	 */
	@Override
	public CompositionGraph clone() {
		CompositionGraph clonedGraph = new CompositionGraph();
		clonedGraph.copyFrom(this);
		return clonedGraph;
	}

	/**
	 * Copies the content (edges and nodes) of the given graph into this instance:
	 */
	public void copyFrom(CompositionGraph otherGraph) {
		this.edges.addAll(otherGraph.getEdges());
		this.nodes.addAll(otherGraph.getNodes());
	}

}
