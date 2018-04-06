package de.upb.sede.composition.graphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class GraphTraversalTest {
	
	@Test public void testBFS() {
		Graph graph = new Graph();
		DummyNode a = new DummyNode("a");
		DummyNode b = new DummyNode("b");
		DummyNode c = new DummyNode("c");
		DummyNode d = new DummyNode("d");
		DummyNode e = new DummyNode("e");
		DummyNode f = new DummyNode("f");
		DummyNode g = new DummyNode("g");
		
		{
			// Adding nodes:
			graph.addNode(a);
			graph.addNode(b);
			graph.addNode(c);
			graph.addNode(d);
			graph.addNode(e);
			graph.addNode(f);
			graph.addNode(g);
			// connecting nodes:
			graph.connectNodes(a, b);
			graph.connectNodes(a, c);
			graph.connectNodes(c, d);
			graph.connectNodes(c, e);
			graph.connectNodes(d, f);
			graph.connectNodes(g, c);
		}
		Iterator<BaseNode> bfsIterator = GraphTraversal.BFS(graph, a).iterator();
		List<BaseNode> layer = new ArrayList<BaseNode>();
		/*
		 * Compare iteration layer by layer:
		 */
		layer.add(bfsIterator.next());
		layer.add(bfsIterator.next());
		Assert.assertTrue(layer.contains(b));
		Assert.assertTrue(layer.contains(c));

		// next layer
		layer.clear();
		layer.add(bfsIterator.next());
		layer.add(bfsIterator.next());
		Assert.assertTrue(layer.contains(d));
		Assert.assertTrue(layer.contains(e));
		
		// next layer
		layer.clear();
		layer.add(bfsIterator.next());
		Assert.assertTrue(layer.contains(f));
	}

}
