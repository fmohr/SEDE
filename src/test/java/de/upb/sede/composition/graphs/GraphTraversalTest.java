package de.upb.sede.composition.graphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.upb.sede.composition.graphs.nodes.BaseNode;

public class GraphTraversalTest {

	Graph graph1 = new Graph();
	
	DummyNode a = new DummyNode("a");
	DummyNode b = new DummyNode("b");
	DummyNode c = new DummyNode("c");
	DummyNode d = new DummyNode("d");
	DummyNode e = new DummyNode("e");
	DummyNode f = new DummyNode("f");
	DummyNode g = new DummyNode("g");
	
	@Before public void createGraph() {
		graph1 = new Graph();
		
		{
			// Adding nodes:
			graph1.addNode(a);
			graph1.addNode(b);
			graph1.addNode(c);
			graph1.addNode(d);
			graph1.addNode(e);
			graph1.addNode(f);
			graph1.addNode(g);
			// connecting nodes:
			graph1.connectNodes(a, b);
			graph1.connectNodes(a, c);
			graph1.connectNodes(c, d);
			graph1.connectNodes(c, e);
			graph1.connectNodes(d, f);
			graph1.connectNodes(g, c);
		}
	}
	
	@Test public void testBFS() {
		Iterator<BaseNode> bfsIterator = GraphTraversal.BFS(graph1, a).iterator();
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
	
	@Test public void testTopologicalSort() {
		Iterator<BaseNode> bfsIterator = GraphTraversal.topologicalSort(graph1).iterator();
		List<BaseNode> layer = new ArrayList<BaseNode>();
		
		/*
		 * Compare iteration layer by layer:
		 */
		layer.add(bfsIterator.next());
		layer.add(bfsIterator.next());
		Assert.assertTrue(layer.contains(a));
		Assert.assertTrue(layer.contains(g));

		// next layer
		layer.clear();
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
	}

}
