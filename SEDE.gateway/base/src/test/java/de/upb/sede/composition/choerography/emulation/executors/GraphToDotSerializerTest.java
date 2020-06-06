package de.upb.sede.composition.choerography.emulation.executors;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import de.upb.sede.composition.graphs.nodes.CompositionGraph;
import de.upb.sede.exec.ExecutorCapabilities;
import de.upb.sede.exec.ExecutorContactInfo;
import de.upb.sede.exec.ExecutorHandle;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.GraphToDot;
import org.junit.Test;

import de.upb.sede.composition.graphs.DummyNode;
import de.upb.sede.composition.graphs.nodes.BaseNode;

public class GraphToDotSerializerTest {
	@Test
	public void graphToDotSerializationTest() {
		ExecutionGraph graph = new ExecutionGraph();
		List<BaseNode> nodes = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			BaseNode node = new DummyNode("" + i);
			graph.addNode(node);
			nodes.add(node);
		}
		for (int i = 0; i < 5; i++) {
			graph.addEdge(nodes.get(i % 10), nodes.get((i + 3) % 10));
		}
        CompositionGraph cg = graph.extractGraph().isClient(true)
            .executorHandle(ExecutorHandle.builder().capabilities(ExecutorCapabilities.builder().build()).contactInfo(ExecutorContactInfo.builder().qualifier("ex1").build()).build())
            .build();
        String dot = GraphToDot.graphToDotString(cg);
		String svg = GraphToDot.graphToSVGString(cg);

		FileUtil.writeStringToFile("testrsc/temp/dot-files/testGraph.dot", dot);
		FileUtil.writeStringToFile("testrsc/temp/dot-files/testGraph.svg", svg);
	}
}
