package de.upb.sede.composition.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.util.FilteredIterator;

/**
 * Defines algorithms to traverse a given graph.
 * All algorithms shall not change the state of any node or the graph.
 * 
 * @author aminfaez
 *
 */
public final class GraphTraversal {
	
	private GraphTraversal() {} // private constructor to disallow instantiation

	/**
	 * Returns an iterable which traverses the tree in BFS order from the given source.
	 */
	public static Iterable<BaseNode> BFS(final Graph graph, final BaseNode source) {

		if (!graph.contains(source)) {
			throw new RuntimeException("Source " + source + " isn't contained by the graph.");
		}
		/**
		 * return bfs-iterable starting from source:
		 */
		return (() -> {
			/**
			 * Create iterator:
			 */
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
						for (BaseNode neighbor : GraphTraversal.neighbors(graph, next)) {
							if (!visitedSet.contains(neighbor)) {
								visitedSet.add(neighbor);
								fifoQueue.addLast(neighbor);
							}
						}
					}
				}
			};
		});
	}
	

	/**
	 * returns a collections of all the neighbors from the given node.
	 */
	public static Iterable<BaseNode> neighbors(final Graph graph, final BaseNode node) {
		return () -> new FilteredIterator<BaseNode>(graph.getNodes().iterator(),
				otherNode -> (node != otherNode && graph.containsEdge(node, otherNode)));
	}
	
	/**
	 * Returns an iterable of all edges that contain the given node.
	 */
	public Iterable<Edge> allEdgesWith(final Graph graph, final BaseNode node) {
		return () -> new FilteredIterator<Edge>(graph.getEdges().iterator(), edge -> edge.contains(node));
	}
	

	/**
	 * Returns true if the given nodes are in the given graph and there is a path from
	 * source to target.
	 */
	public boolean isTherePathFromTo(final Graph graph, final BaseNode source, final BaseNode target) {
		if(graph.contains(source) && graph.contains(target)) {
			/*
			 * Do breath first search over the graph and find the target node.
			 */
			for(BaseNode traversingNode : GraphTraversal.BFS(graph, source)) {
				if(traversingNode.equals(target)) {
					return true;
				}
			}
			/*
			 * target node was not found. return false
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
	 * Iterator of all nodes  that changes the state of the given fieldname.
	 * 
	 * ClassesConfig is needed because based on the configuration of the classes some methods do change the state of a service and some dont.  
	 */
	public static Iterable<BaseNode> fieldnameProducingNodes(final Graph graph, final String fieldname, final ClassesConfig configuration) {
		return () -> new FilteredIterator<>(graph.getNodes().iterator(), node -> node.producesField(fieldname, configuration));
	}
}
