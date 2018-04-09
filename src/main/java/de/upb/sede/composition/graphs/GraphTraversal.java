package de.upb.sede.composition.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.gc.ResolveInfo;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.exceptions.GraphFormException;
import de.upb.sede.util.ChainedIterator;
import de.upb.sede.util.ChainedIteratorTest;
import de.upb.sede.util.FilteredIterator;
import de.upb.sede.util.Iterators;

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
	 * Doesn't include source in the iteration.
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
			Graph clonedGraph = graph.clone(); // clone graph to be able to remove edges thus improved time efficiency
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
						for (BaseNode neighbor : GraphTraversal.neighbors(clonedGraph, next)) {
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
	 * returns a collections of all the neighbors from the given node.
	 */
	public static Iterable<BaseNode> neighbors(final Graph graph, final BaseNode node) {
		return () -> new FilteredIterator<BaseNode>(graph.getNodes().iterator(),
				otherNode -> (node != otherNode && graph.containsEdge(node, otherNode)));
	}
	
	/**
	 * Returns an iterable of all edges that contain the given node.
	 */
	public static Iterable<Edge> allEdgesWith(final Graph graph, final BaseNode node) {
		return () -> new FilteredIterator<Edge>(graph.getEdges().iterator(), edge -> edge.contains(node));
	}
	

	/**
	 * Returns true if the given nodes are in the given graph and there is a path from
	 * source to target.
	 */
	public static boolean isTherePathFromTo(final Graph graph, final BaseNode source, final BaseNode target) {
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
	 * Iterator of all nodes  that changes the state of the given fieldname.
	 * 
	 * ClassesConfig is needed because based on the configuration of the classes some methods do change the state of a service and some dont.  
	 */
	public static Iterable<BaseNode> fieldnameProducingNodes(final Graph graph, final String fieldname, final ResolveInfo resolveInfo) {
		return () -> new FilteredIterator<>(graph.getNodes().iterator(), node -> node.producesField(fieldname, resolveInfo));
	}
	
	/**
	 * Returns a set of all producing field names of a graph.
	 * The set doesn't contain constant values.
	 */
	public static Set<String> producedFields(final Graph graph, final ResolveInfo resolveInfo){
		Set<String> producedFields = new HashSet<>();
		/* collect all fields */
		for(BaseNode bn : graph.getNodes()) {
			producedFields.addAll(bn.producingFields(resolveInfo));
		}
		/*
		 * Remove all constatns.
		 */
		producedFields.removeIf(FMCompositionParser::isConstant);
		return producedFields;
	}
	
	/**
	 * Returns true if there is no edge in the given graph that targets the given node.
	 */
	public static boolean isIndependent(Graph graph, BaseNode baseNode) {
		return graph.getEdges().stream().allMatch(e -> !e.getTo().equals(baseNode));
	}
	
	/**
	 * Returns an iterable of all independent nodes (nodes which aren't dependent on other nodes)
	 */
	public static Iterable<BaseNode> independentNodes(Graph graph){
		return () -> new FilteredIterator<>(graph.getNodes().iterator(), node -> isIndependent(graph, node));
	}
	
	/**
	 * Flattens the given graph to a list with Topological sorting of its nodes.
	 * (The order of the list matters because nodes are independent of the nodes with higher indices.)
	 * By flattening the graph information about concrete dependency is lost thus one cannot go back from the list representation to the graph one.
	 */
	public static List<BaseNode> topologicalSort(final Graph graph){
		List<BaseNode> topologicalSortedList = new ArrayList<>();
		Graph clonedGraph = graph.clone();
		/*
		 *  While the graph isn't empty add independent nodes to the topologicalSort list.
		 */
		while(!clonedGraph.isEmpty()) {
			List<BaseNode> independentNodes = Iterators.TO_LIST(independentNodes(clonedGraph).iterator());
			if(independentNodes.isEmpty()) {
				/*
				 * There are no independent nodes but the graph is not empty yet.
				 * That means the graph is not acyclic:
				 */
				throw new GraphFormException("Graph is not acyclic");
			}
			/*
			 * Add the nodes to the topological sorted list
			 */
			topologicalSortedList.addAll(independentNodes);
			independentNodes.forEach(clonedGraph::removeNode);
		}
		return Collections.unmodifiableList(topologicalSortedList);
	}

	public static Iterable<BaseNode> iterateNodes(final Graph graph){
		return graph.getNodes();
	}
	public static Iterable<BaseNode> iterateNodesWithClassname(final Graph graph, final String simpleNodeClassName){
		return () -> new FilteredIterator<>(iterateNodes(graph).iterator(), n -> (n.getClass().getSimpleName().equals(simpleNodeClassName)));
	}
}
