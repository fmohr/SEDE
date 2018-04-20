package de.upb.sede.util;

import java.util.HashSet;
import java.util.Set;

import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.Edge;
import de.upb.sede.composition.graphs.GraphTraversal;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import info.leadinglight.jdot.Graph;
import info.leadinglight.jdot.Node;
import info.leadinglight.jdot.enums.Shape;

public class GraphToDotSerializer {

	public static String getDOTForGraph(CompositionGraph compGraph) {
		Set<BaseNode> alreadySeenBaseNodes = new HashSet<>();
		Graph graph = new Graph();
		for (Edge edge : GraphTraversal.iterateEdges(compGraph)) {
			BaseNode fromNode = edge.getFrom();
			BaseNode toNode = edge.getTo();
			if (!alreadySeenBaseNodes.contains(fromNode)){
				Node dotFromNode = new Node(fromNode.toString()).setShape(Shape.circle);
				graph.addNode(dotFromNode);
				alreadySeenBaseNodes.add(fromNode);
			}
			if (!alreadySeenBaseNodes.contains(toNode)) {
				Node dotToNode = new Node(toNode.toString()).setShape(Shape.circle);
				graph.addNode(dotToNode);
				alreadySeenBaseNodes.add(toNode);
			}
			info.leadinglight.jdot.Edge dotEdge = new info.leadinglight.jdot.Edge();
			dotEdge.addNode(fromNode.toString()).addNode(toNode.toString());
			graph.addEdge(dotEdge);
		}
		//Adding those nodes that are adjacent to no edge.
		for(BaseNode baseNode : GraphTraversal.iterateNodes(compGraph)) {
			if(!alreadySeenBaseNodes.contains(baseNode)){
				Node dotNode = new Node(baseNode.toString()).setShape(Shape.circle);
				graph.addNode(dotNode);
			}
		}
		return graph.toDot();
	}
}
