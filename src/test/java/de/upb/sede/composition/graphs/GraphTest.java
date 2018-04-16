package de.upb.sede.composition.graphs;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class GraphTest {

	DummyNode a = new DummyNode("a");
	DummyNode b = new DummyNode("b");
	DummyNode c = new DummyNode("c");
	DummyNode d = new DummyNode("d");
	DummyNode e = new DummyNode("e");
	DummyNode f = new DummyNode("f");
	DummyNode g = new DummyNode("g");

	@Test
	public void testNodeSet() {
		CompositionGraph graph1 = new CompositionGraph();
		Assert.assertEquals(0, graph1.getNodes().size());
		
		graph1.addNode(a);
		Assert.assertEquals(1, graph1.getNodes().size());
		graph1.addNode(b);
		Assert.assertEquals(2, graph1.getNodes().size());
		Assert.assertTrue(graph1.getNodes().contains(a));
		Assert.assertTrue(graph1.getNodes().contains(b));
		Assert.assertFalse(graph1.getNodes().contains(c));
		
		graph1.addNode(a);
		graph1.addNode(b);
		Assert.assertEquals(2, graph1.getNodes().size());
		Assert.assertTrue(graph1.getNodes().contains(a));
		Assert.assertTrue(graph1.getNodes().contains(b));
		Assert.assertFalse(graph1.getNodes().contains(c));
		
		graph1.removeNode(c);
		Assert.assertEquals(2, graph1.getNodes().size());
		Assert.assertTrue(graph1.getNodes().contains(a));
		Assert.assertTrue(graph1.getNodes().contains(b));
		Assert.assertFalse(graph1.getNodes().contains(c));
		
		graph1.removeNode(a);
		Assert.assertEquals(1, graph1.getNodes().size());
		Assert.assertFalse(graph1.getNodes().contains(a));
		Assert.assertTrue(graph1.getNodes().contains(b));
		Assert.assertFalse(graph1.getNodes().contains(c));
		
		CompositionGraph graph2 = graph1.clone();
		Assert.assertEquals(1, graph2.getNodes().size());
		Assert.assertFalse(graph2.getNodes().contains(a));
		Assert.assertTrue(graph2.getNodes().contains(b));
		Assert.assertFalse(graph2.getNodes().contains(c));

		graph1.addNode(d);
		Assert.assertEquals(2, graph1.getNodes().size());
		Assert.assertEquals(1, graph2.getNodes().size());
		
		graph2.addNode(e);
		Assert.assertEquals(2, graph2.getNodes().size());
		Assert.assertFalse(graph1.contains(e));
		Assert.assertFalse(graph2.contains(d));
		
		graph2.copyFrom(graph1);
		Assert.assertEquals(2, graph1.getNodes().size());
		Assert.assertEquals(3, graph2.getNodes().size());
		Assert.assertTrue(graph2.contains(d));
		
		boolean exceptionThrown = false;
		try {
			graph1.addNode(null);
		} catch(NullPointerException ex) {
			exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
	}
	


	@Test
	public void testEdgeSet() {
		CompositionGraph graph1 = new CompositionGraph();
		graph1.connectNodes(a, b);
		Assert.assertEquals(1, graph1.getEdges().size());
		Assert.assertTrue(graph1.getEdges().contains(new Edge(a,b)));
		Assert.assertFalse(graph1.getEdges().contains(new Edge(b,a)));
		Assert.assertFalse(graph1.getEdges().contains(new Edge(a,a)));
		
		graph1.connectNodes(a, b);
		Assert.assertEquals(1, graph1.getEdges().size());
		Assert.assertTrue(graph1.getEdges().contains(new Edge(a,b)));
		Assert.assertFalse(graph1.getEdges().contains(new Edge(b,a)));
		Assert.assertFalse(graph1.getEdges().contains(new Edge(a,a)));

		graph1.connectNodes(a, c);
		graph1.connectNodes(b, c);
		Assert.assertEquals(3, graph1.getEdges().size());
		
		
		graph1.removeNode(a);
		Assert.assertEquals(1, graph1.getEdges().size());
		Assert.assertFalse(graph1.getEdges().contains(new Edge(a,b)));
		Assert.assertFalse(graph1.getEdges().contains(new Edge(a,c)));
		Assert.assertTrue(graph1.getEdges().contains(new Edge(b,c)));
		

		boolean exceptionThrown = false;
		try {
			graph1.connectNodes(a, null);
		} catch(NullPointerException ex) {
			exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
	}
}
