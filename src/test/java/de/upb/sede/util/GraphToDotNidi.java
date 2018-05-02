package de.upb.sede.util;

import java.util.HashMap;
import java.util.Map;

import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.DependencyEdge;
import de.upb.sede.composition.graphs.GraphTraversal;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizCmdLineEngine;
import guru.nidi.graphviz.engine.Renderer;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;
import guru.nidi.graphviz.service.DefaultExecutor;
import info.leadinglight.jdot.impl.Util;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphToDotNidi {
	
	private static final String PATH_TO_DOT = "/usr/local/bin/dot";
	
	static {

		/*
		 * append folders of where the dot program lies:
		 */
//		String DOT_ENV = "/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/bin";
//		Graphviz.useEngine((new GraphvizCmdLineEngine(DOT_ENV, new DefaultExecutor())));
	}
	
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
		System.out.println(graph.toString());
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
