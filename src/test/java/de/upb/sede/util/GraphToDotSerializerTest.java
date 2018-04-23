package de.upb.sede.util;

import java.util.ArrayList;
import java.util.List;

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
		FileUtil.writeStringToFile("testrsc/test.dot", GraphToDotSerializer.getDOTForGraph(graph));
	}
}
