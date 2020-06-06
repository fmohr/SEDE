package de.upb.sede.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.upb.sede.composition.graphs.nodes.ICompositionGraph;
import de.upb.sede.requests.resolve.beta.IChoreography;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.o4.tinyjavadot.DotGraph;
import de.upb.o4.tinyjavadot.DotNode;
import de.upb.sede.composition.graphs.nodes.BaseNode;


public class GraphToDot {

	private final static Logger logger = LoggerFactory.getLogger(GraphToDot.class);

	private static final String PATH_TO_DOT;

	private static final boolean DISABLED;

	static {
		/*
		 * Read the path to dot from environment variable: 'DOT_PATH'
		 */
		Map<String, String> env = System.getenv();
		if(System.getenv().containsKey("DOT_PATH") && System.getenv().get("DOT_PATH") != null) {
			PATH_TO_DOT = System.getenv().get("DOT_PATH");
			logger.info("DOT_PATH: {}", PATH_TO_DOT);
		} else {
			PATH_TO_DOT = "/usr/local/bin/dot";
			logger.info("Environment variable 'DOT_PATH' isn't defined.");
			logger.info("Using {} as default path to dot. Change default in: GraphToDot.java", PATH_TO_DOT);
		}

		if(! new File(PATH_TO_DOT).exists()) {
			logger.warn("Dot doesn't exist on the specified path: {}. DISABLED GraphToDot.java", PATH_TO_DOT);
			DISABLED = true;
		} else {
			DISABLED = false;
		}
	}

	private static void addNodesToGraph(DotGraph graph, Map<BaseNode, DotNode> nodeMap, ICompositionGraph compGraph, boolean dotted) {
		for (BaseNode baseNode : compGraph.getNodes()) {
			if(nodeMap.containsKey(baseNode)) {
				continue;
			}
			DotNode node = new DotNode(toString(baseNode));
			node.setShape(DotNode.Shape.box);
			nodeMap.put(baseNode, node);
			graph.addNode(node);
		}
		for (BaseNode baseNode : compGraph.getNodes()) {
			DotNode dotNode = nodeMap.get(baseNode);
            List<Long> targets = compGraph.getEdges().get(baseNode.getIndex().get().toString());
            for(BaseNode neighbor : compGraph.getNodes()) {
                if(!targets.contains(neighbor.getIndex().get())) {
                    continue;
                }
				DotNode dotNeighbor = nodeMap.get(neighbor);
				graph.connect(dotNode, dotNeighbor).style(dotted?DotGraph.EdgeStyle.dotted : DotGraph.EdgeStyle.filled);
			}
		}
	}

	private static String toString(BaseNode baseNode) {
		return baseNode.toString().replaceAll("\"", "");
	}

	public static String graphToDotString(ICompositionGraph compGraph) {
		DotGraph dotGraph = new DotGraph();
		Map<BaseNode, DotNode> nodeMap = new HashMap<>();
		addNodesToGraph(dotGraph, nodeMap, compGraph, false);
		return dotGraph.toDot();
	}

	public static String graphToSVGString(ICompositionGraph compGraph) {
		return formatDotToSVG(graphToDotString(compGraph));
	}

	private static DotGraph createClusterGraphs(List<ICompositionGraph> comps) {
		DotGraph clusterGraph = new DotGraph();
		Map<BaseNode, DotNode> nodeMap = new HashMap<>();
        for (ICompositionGraph comp : comps) {
            String executorId = comp.getExecutorHandle().getQualifier();
            if(comp.isClient()) {
                executorId += " : Client";
            }
			DotGraph innerGraph = new DotGraph();
			addNodesToGraph(innerGraph, nodeMap, comp, false);
			DotNode executorNameBox = new DotNode(executorId);
			executorNameBox.setShape(DotNode.Shape.diamond);
			innerGraph.addNode(executorNameBox);

			clusterGraph.addGraph(innerGraph);
		}
		return clusterGraph;
	}

	public static String choreographyToDot(IChoreography gc) {
        List<ICompositionGraph> graphs = gc.getCompositionGraph();

		return createClusterGraphs(graphs).toDot();
	}

	public static String choreographyToSag(IChoreography gc) {
		return formatDotToSVG(choreographyToDot(gc));
	}

	private static String formatDot(String dot, String format) {
		if(DISABLED){
			return "";
		}
		else {
			return ShellUtil.sh(new String[]{PATH_TO_DOT, "-T" + format}, dot);
		}
	}

	private static String formatDotToSVG(String dot) {
		return formatDot(dot, "svg");
	}

}
