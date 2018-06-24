package de.upb.sede.util;

import de.upb.o4.tinyjavadot.DotNode;
import de.upb.o4.tinyjavadot.DotGraph;
import de.upb.sede.composition.graphs.*;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.util.ShellUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class GraphToDot {

	private final static Logger logger = LogManager.getLogger();
	
	private static final String PATH_TO_DOT;

	static {
		/*
		 * Read the path to dot from environment variable: 'DOT_PATH'
		 */
		if(System.getenv().containsKey("DOT_PATH")) {
			PATH_TO_DOT = System.getenv().get(System.getenv().get("DOT_PATH"));
		} else {
			PATH_TO_DOT = "/usr/local/bin/dot";
		}

		if(! new File(PATH_TO_DOT).exists()) {
			logger.warn("Dot doesn't exist on the specified path: {}", PATH_TO_DOT);
		} else {
			logger.info("Path to dot: {}", PATH_TO_DOT);
		}
	}

	private static void addNodesToGraph(DotGraph graph, Map<BaseNode, DotNode> nodeMap, CompositionGraph compGraph, boolean dotted) {
		for (BaseNode baseNode : GraphTraversal.iterateNodes(compGraph)) {
			if(nodeMap.containsKey(baseNode)) {
				continue;
			}
			DotNode node = new DotNode(toString(baseNode));
			node.setShape(DotNode.Shape.box);
			nodeMap.put(baseNode, node);
			graph.addNode(node);
		}
		for (BaseNode baseNode : GraphTraversal.iterateNodes(compGraph)) {
			DotNode dotNode = nodeMap.get(baseNode);
			for(BaseNode neighbor : GraphTraversal.neighbors(compGraph, baseNode)) {
				DotNode dotNeighbor = nodeMap.get(neighbor);
				graph.connect(dotNode, dotNeighbor).style(dotted?DotGraph.EdgeStyle.dotted : DotGraph.EdgeStyle.filled);
			}
		}
	}

	private static String toString(BaseNode baseNode) {
		return baseNode.toString().replaceAll("\"", "");
	}

	public static String graphToDotString(CompositionGraph compGraph) {
		DotGraph dotGraph = new DotGraph();
		Map<BaseNode, DotNode> nodeMap = new HashMap<>();
		addNodesToGraph(dotGraph, nodeMap, compGraph, false);
		return dotGraph.toDot();
	}

	public static String graphToSVGString(CompositionGraph compGraph) {
		return formatDotToSVG(graphToDotString(compGraph));
	}

	private static DotGraph createClusterGraphs(Map<String, CompositionGraph> comps, CompositionGraph tranmissionGraph) {
		DotGraph clusterGraph = new DotGraph();
		Map<BaseNode, DotNode> nodeMap = new HashMap<>();
		for(String executorID : comps.keySet()) {
			CompositionGraph cg = comps.get(executorID);
			DotGraph innerGraph = new DotGraph();
			addNodesToGraph(innerGraph, nodeMap, cg, false);
			DotNode executorNameBox = new DotNode(executorID);
			executorNameBox.setShape(DotNode.Shape.diamond);
			innerGraph.addNode(executorNameBox);

			clusterGraph.addGraph(innerGraph);
		}
		addNodesToGraph(clusterGraph, nodeMap, tranmissionGraph, true);
		return clusterGraph;
	}

	public static String GCToDot(GraphConstruction gc) {
		Map<String, CompositionGraph> comps = gc.getInvolvedExecutions();
		return createClusterGraphs(comps, gc.getTransmissionGraph()).toDot();
	}

	public static String GCToSVG(GraphConstruction gc) {
		return formatDotToSVG(GCToDot(gc));
	}

	private static String formatDot(String dot, String format) {
		return ShellUtil.sh(new String[]{PATH_TO_DOT, "-T" + format}, dot);
	}
	
	private static String formatDotToSVG(String dot) {
		return formatDot(dot, "svg");
	}
	
}
