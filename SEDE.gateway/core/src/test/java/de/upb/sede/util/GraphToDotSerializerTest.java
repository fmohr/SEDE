package de.upb.sede.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import de.upb.sede.composition.graphs.GraphConstruction;
import de.upb.sede.util.FileUtil;
import org.junit.Test;

import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.DummyNode;
import de.upb.sede.composition.graphs.DependencyEdge;
import de.upb.sede.composition.graphs.nodes.BaseNode;

public class GraphToDotSerializerTest {
	@Test
	public void graphToDotSerializationTest() {
		CompositionGraph graph = new CompositionGraph();
		List<BaseNode> nodes = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			BaseNode node = new DummyNode("" + i);
			graph.addNode(node);
			nodes.add(node);
		}
		for (int i = 0; i < 5; i++) {
			DependencyEdge edge = new DependencyEdge(nodes.get(i % 10), nodes.get((i + 3) % 10));
			graph.addEdge(edge);
		}
		String dot = GraphToDot.graphToDotString(graph);
		String svg = GraphToDot.graphToSVGString(graph);

		FileUtil.writeStringToFile("testrsc/temp/dot-files/testGraph.dot", dot);
		FileUtil.writeStringToFile("testrsc/temp/dot-files/testGraph.svg", svg);
	}
}
