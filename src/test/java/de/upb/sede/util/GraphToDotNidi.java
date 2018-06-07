package de.upb.sede.util;

import de.upb.sede.composition.graphs.*;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

import java.util.HashMap;
import java.util.Map;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphToDotNidi {
	
	private static final String PATH_TO_DOT = "/usr/local/bin/dot";
	
	
	private static Graph buildDotGraph(Graph graph, CompositionGraph compGraph, boolean dotted) {
		Map<BaseNode, Node> nodeMap = new HashMap<>();
		for (BaseNode baseNode : GraphTraversal.iterateNodes(compGraph)) {
			Node n = node(baseNode.hashCode()+"").with(Shape.RECTANGLE, Label.of(toString(baseNode)));
			nodeMap.put(baseNode, n);
			graph = graph.with(n);
		}
		for (BaseNode baseNode : GraphTraversal.iterateNodes(compGraph)) {
			Node node = node(baseNode.hashCode() + "");
			for(BaseNode neighbor : GraphTraversal.neighbors(compGraph, baseNode)) {
				node = node.link(to(node(neighbor.hashCode()+"")).with(dotted ? Style.DOTTED : Style.FILLED));
			}
			graph = graph.with(node);
		}
		return graph;
	}
	
	private static String toString(BaseNode baseNode) {
		return baseNode.toString().replaceAll("\"", "");
	}

	private static Graph clusterFromCompGraph(String name, CompositionGraph compGraph) {
		Graph g = graph(name).directed().cluster();
		g = g.with(node(name).with(Shape.HOUSE, Color.ORANGE,
                Style.lineWidth(2).and(Style.SOLID)));
		g = buildDotGraph(g, compGraph, false);
		return g;
	}

	private static Graph graphFromCompGraphs(Map<String, CompositionGraph> compositionGraphs) {
		Graph graph = graph().directed();
		for(String compGraph : compositionGraphs.keySet()) {
			Graph cluster  = clusterFromCompGraph(compGraph, compositionGraphs.get(compGraph));
			graph = graph.with(cluster);
		}
		return graph;
	}
	
	public static String getSVGForGraphs(Map<String, CompositionGraph> compositionGraphs, 
			CompositionGraph transitionGraph) {
		Graph g = graphFromCompGraphs(compositionGraphs);
		if(transitionGraph!=null) {
			g = buildDotGraph(g, transitionGraph, true);
		}
		return formatDotToSVG(g.toString());
	}

	public static String getSVGForGC(GraphConstruction gc) {
		Map<String, CompositionGraph> comps = new HashMap<>();
		DataFlowAnalysis df = gc.getDataFlow();
		String clientId = df.getClientExecPlan().getExecutor().getExecutorId();
		comps.put(clientId, df.getClientExecPlan().getGraph());
		for(ExecPlan exec : df.getInvolvedExecutions()) {
			if(exec.getExecutor().getExecutorId().equals(clientId)) {
				continue;
			}
			comps.put(exec.getExecutor().getExecutorId(), exec.getGraph());
		}
		return getSVGForGraphs(comps, df.getTransmissionGraph());
	}

	public static String getDOTForGraph(CompositionGraph compGraph) {
		Graph graph = graph().directed();
		graph = buildDotGraph(graph, compGraph, false);
		return graphToDot(graph);
	}
	
	public static String getSVGForGraph(CompositionGraph compGraph) {
		return formatDotToSVG(getDOTForGraph(compGraph));
	}
	
	private static String graphToDot(Graph graph) {
		return graph.toString();
	}
	
	private static String formatDot(String dot, String format) {
		return ShellUtil.sh(new String[]{PATH_TO_DOT, "-T" + format}, dot);
	}
	
	private static String formatDotToSVG(String dot) {
		return formatDot(dot, "svg");
	}
	
}
