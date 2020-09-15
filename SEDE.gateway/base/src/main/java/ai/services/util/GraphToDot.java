package ai.services.util;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.services.composition.graphs.nodes.ICompositionGraph;
import ai.services.requests.resolve.beta.IChoreography;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.o4.tinyjavadot.DotGraph;
import de.upb.o4.tinyjavadot.DotNode;
import ai.services.composition.graphs.nodes.BaseNode;


public class GraphToDot {

	private static final Logger logger = LoggerFactory.getLogger(GraphToDot.class);

	private static final String PATH_TO_DOT = new SystemSettingLookup(
	    "/usr/local/bin/dot",
        "sede.gw.DotPath",
        "DOT_PATH"
    ).lookup();

	private static final boolean DISABLED;

	static {
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
            List<Long> targets = compGraph.getEdges().getOrDefault(baseNode.getIndex().toString(), Collections.EMPTY_LIST);
            for(BaseNode neighbor : compGraph.getNodes()) {
                if(!targets.contains(neighbor.getIndex())) {
                    continue;
                }
				DotNode dotNeighbor = nodeMap.get(neighbor);
				graph.connect(dotNode, dotNeighbor).style(dotted?DotGraph.EdgeStyle.dotted : DotGraph.EdgeStyle.filled);
			}
		}
	}

	private static String toString(BaseNode baseNode) {
		return baseNode.getText().replaceAll("\"", "");
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
