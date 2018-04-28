package de.upb.sede.util;

import java.util.Map;

import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.DependencyEdge;
import de.upb.sede.composition.graphs.GraphTraversal;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import info.leadinglight.jdot.ClusterGraph;
import info.leadinglight.jdot.Edge;
import info.leadinglight.jdot.Graph;
import info.leadinglight.jdot.Node;
import info.leadinglight.jdot.enums.Shape;
import info.leadinglight.jdot.impl.AbstractGraph;

public class GraphToDot {
	
	static {
		/*
		 * change the path if necessary.
		 */
		String pathToDotApp = "/usr/local/bin/dot";
//		Graph.setDefaultCmd(pathToDotApp);
	}
	
	private static void buildDotGraph(AbstractGraph graph, CompositionGraph compGraph) {
		for (BaseNode baseNode : GraphTraversal.iterateNodes(compGraph)) {
			Node dotNode = new Node(toString(baseNode)).setShape(Shape.box);
			graph.addNode(dotNode);
		}
		for (DependencyEdge edge : GraphTraversal.iterateEdges(compGraph)) {
			BaseNode fromNode = edge.getFrom();
			BaseNode toNode = edge.getTo();
			Edge dotEdge = new Edge();
			dotEdge.addNode(toString(fromNode)).addNode(toString(toNode));
			graph.addEdge(dotEdge);
		}
	}
	private static String toString(BaseNode baseNode) {
		return baseNode.toString().replaceAll("\"", "'");
	}

	private static ClusterGraph clusterFromCompGraph(String name, CompositionGraph compGraph) {
		ClusterGraph graph = new ClusterGraph(name);
		buildDotGraph(graph, compGraph);
		return graph;
	}

	private static Graph graphFromCompGraphs(Map<String, CompositionGraph> compositionGraphs) {
		Graph graph = new Graph();
		for(String compGraph : compositionGraphs.keySet()) {
			graph.addClusterGraph(clusterFromCompGraph(compGraph, compositionGraphs.get(compGraph)));
		}
		return graph;
	}
	public static String getSVGForGraphs(Map<String, CompositionGraph> compositionGraphs) {
		Graph graph = new Graph();
		for(String compGraph : compositionGraphs.keySet()) {
			graph.addClusterGraph(clusterFromCompGraph(compGraph, compositionGraphs.get(compGraph)));
		}
		return graph.dot2svg();
	}

	public static String getDOTForGraph(CompositionGraph compGraph) {
		Graph graph = new Graph();
		buildDotGraph(graph, compGraph);
		return graph.toDot();
	}
	
	public static String getSVGForGraph(CompositionGraph compGraph) {
		Graph graph = new Graph();
		buildDotGraph(graph, compGraph);
		return graph.dot2svg();
	}
	
}
