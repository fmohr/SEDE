package ai.services.composition.choerography.emulation.executors;

import ai.services.composition.graphs.nodes.BaseNode;
import ai.services.exceptions.GraphFormException;
import ai.services.util.FilteredIterator;
import ai.services.util.Iterators;

import java.util.*;

/**
 * Defines algorithms to traverse a given graph. All algorithms shall not change
 * the state of any node or the graph.
 *
 * @author aminfaez
 *
 */
public final class GraphTraversal {

	private GraphTraversal() {
	} // private constructor to disallow instantiation

	/**
	 * Returns an iterable which traverses the tree in BFS order from the given
	 * source. Doesn't include source in the iteration.
	 */
	public static Iterable<BaseNode> BFS(final ExecutionGraph graph, final BaseNode source) {

		if (!graph.contains(source)) {
			throw new RuntimeException("Source " + source + " isn't contained by the graph.");
		}
		/*
		 * return bfs-iterable starting from source:
		 */
		return (() -> {
			/*
			 * Create iterator:
			 */
			ExecutionGraph clonedGraph = graph.shallowClone(); // clone graph to be able to remove edges thus improved time
															// efficiency
			Deque<BaseNode> fifoQueue = new ArrayDeque<>();
			Set<BaseNode> visitedSet = new HashSet<>();

			fifoQueue.addLast(source);
			return new Iterator<BaseNode>() {
				BaseNode next;
				{
					findNext(); // skip source node
					findNext(); // now next points to a node that source node is connected to.
				}

				@Override
				public boolean hasNext() {
					return next != null;
				}

				@Override
				public BaseNode next() {
					BaseNode returnValue = this.next;
					this.findNext();
					return returnValue;
				}

				/**
				 * Finds the next node in bfs manner.
				 */
				private void findNext() {
					next = fifoQueue.poll();
					if (next != null) {
						for (BaseNode neighbor : GraphTraversal.targetingNodes(clonedGraph, next)) {
							if (!visitedSet.contains(neighbor)) {
								visitedSet.add(neighbor);
								fifoQueue.addLast(neighbor);
							}
						}
						clonedGraph.removeNode(next);
					}
				}
			};
		});
	}

	/**
	 * returns an iterable of all the neighbors from the given node.
	 */
	public static Iterable<BaseNode> targetingNodes(final ExecutionGraph graph, final BaseNode node) {
		return () -> new FilteredIterator<BaseNode>(graph.getNodes().iterator(),
				otherNode -> (node != otherNode && graph.containsEdge(node, otherNode)));
	}

	/**
	 * returns an iterable of all the nodes from the given graph that have an edge targeting the given node.
	 * Thus the given node is the neighbor of all iterated nodes.
	 */
	public static Iterable<BaseNode> sourceNodes(final ExecutionGraph graph, final BaseNode node) {
		return () -> new FilteredIterator<>(graph.getNodes().iterator(),
            otherNode -> (node != otherNode && graph.containsEdge(otherNode, node)));
	}

	/**
	 * Returns an iterable of all edges that contain the given node.
	 */
	public static Iterable<ExecutionGraph.GraphEdge> allEdgesWith(final ExecutionGraph graph, final BaseNode node) {
		return () -> new FilteredIterator<>(graph.getEdges().iterator(), edge -> edge.contains(node));
	}

	/**
	 * Returns true if the given nodes are in the given graph and there is a path
	 * from source to target.
	 */
	public static boolean isTherePathFromTo(final ExecutionGraph graph, final BaseNode source,
			final BaseNode target) {
		if (graph.contains(source) && graph.contains(target)) {
			/*
			 * Do breath first search over the graph and find the target node.
			 */
			for (BaseNode traversingNode : GraphTraversal.BFS(graph, source)) {
				if (traversingNode.equals(target)) {
					return true;
				}
			}
			/*
			 * target node was not found.
			 */
			return false;
		} else {
			/*
			 * Source or target is not contained by the given graph.
			 */
			return false;
		}
	}

	/**
	 * Returns true if there is no edge in the given graph that targets the given
	 * node.
	 */
	public static boolean isIndependent(ExecutionGraph graph, BaseNode baseNode) {
		return graph.getEdges().stream().noneMatch(e -> e.getTo().equals(baseNode));
	}

	/**
	 * Returns an iterable of all independent nodes (nodes which aren't dependent on
	 * other nodes)
	 */
	public static Iterable<BaseNode> independentNodes(ExecutionGraph graph) {
		return () -> new FilteredIterator<>(graph.getNodes().iterator(), node -> isIndependent(graph, node));
	}

	/**
	 * Returns true if there is no edge in the given graph that leaves from the given
	 * node.
	 */
	public static boolean isLast(ExecutionGraph graph, BaseNode baseNode) {
		return graph.getEdges().stream()
            .noneMatch(e -> e.getFrom().equals(baseNode));
	}

	/**
	 * Returns an iterable of all last nodes (nodes which have no other node be dependent on them)
	 */
	public static Iterable<BaseNode> lastNodes(ExecutionGraph graph) {
		return () -> new FilteredIterator<>(graph.getNodes().iterator(), node -> isLast(graph, node));
	}

	/**
	 * Flattens the given graph to a list with Topological sorting of its nodes.
	 * (The order of the list matters because nodes are independent of the nodes
	 * with higher indices.) By flattening the graph information about concrete
	 * dependency is lost thus one cannot go back from the list representation to
	 * the graph one.
	 */
	public static List<BaseNode> topologicalSort(final ExecutionGraph graph) {
		List<BaseNode> topologicalSortedList = new ArrayList<>();
		ExecutionGraph clonedGraph = graph.shallowClone();
		/*
		 * While the graph isn't empty add independent nodes to the topologicalSort
		 * list.
		 */
		while (!clonedGraph.isEmpty()) {
			List<BaseNode> independentNodes = Iterators.TO_LIST(independentNodes(clonedGraph).iterator());
			if (independentNodes.isEmpty()) {
				/*
				 * There are no independent nodes but the graph is not empty yet. That means the
				 * graph is not acyclic:
				 */
				throw new GraphFormException("Graph is not acyclic");
			}
			/*
			 * Add the nodes to the topological sorted list
			 */
			topologicalSortedList.addAll(independentNodes);
			independentNodes.forEach(clonedGraph::removeNode);
		}
		return topologicalSortedList;
	}

	public static Iterable<ExecutionGraph.GraphEdge> iterateEdges(final ExecutionGraph graph) {
		return graph.getEdges();
	}

	public static Iterable<BaseNode> iterateNodes(final ExecutionGraph graph) {
		return graph.getNodes();
	}

	public static <T>Iterable<T> iterateNodesWithClass(final ExecutionGraph graph,
													   final Class<T> nodeClass) {
		return () -> new FilteredIterator<T>(((Iterator<T>) iterateNodes(graph).iterator()),
            nodeClass::isInstance);
	}

}
