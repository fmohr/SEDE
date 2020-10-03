package ai.services.composition.choerography.emulation.executors;

import ai.services.composition.graphs.DummyNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.StreamSupport;


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
		ExecutionGraph graph1 = new ExecutionGraph();
		Assert.assertEquals(0, StreamSupport.stream(graph1.getNodes().spliterator(), false).count());

		graph1.addNode(a);
		Assert.assertEquals(1, StreamSupport.stream(graph1.getNodes().spliterator(), false).count());
		graph1.addNode(b);
		Assert.assertEquals(2, StreamSupport.stream(graph1.getNodes().spliterator(), false).count());
		Assert.assertTrue(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == a));
		Assert.assertTrue(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == b));
		Assert.assertFalse(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == c));

		graph1.addNode(a);
		graph1.addNode(b);
		Assert.assertEquals(2, StreamSupport.stream(graph1.getNodes().spliterator(), false).count());
		Assert.assertTrue(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == a));
		Assert.assertTrue(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == b));
		Assert.assertFalse(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == c));

		graph1.removeNode(c);
		Assert.assertEquals(2, StreamSupport.stream(graph1.getNodes().spliterator(), false).count());
		Assert.assertTrue(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == a));
		Assert.assertTrue(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == b));
		Assert.assertFalse(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == c));

		graph1.removeNode(a);
		Assert.assertEquals(1, StreamSupport.stream(graph1.getNodes().spliterator(), false).count());
		Assert.assertFalse(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == a));
		Assert.assertTrue(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == b));
		Assert.assertFalse(StreamSupport.stream(graph1.getNodes().spliterator(), false).anyMatch(n -> n == c));

		ExecutionGraph graph2 = graph1.shallowClone();
		Assert.assertEquals(1, StreamSupport.stream(StreamSupport.stream(graph2.getNodes().spliterator(), false).spliterator(), false).count());
		Assert.assertFalse(StreamSupport.stream(graph2.getNodes().spliterator(), false).anyMatch(n -> n == a));
		Assert.assertTrue(StreamSupport.stream(graph2.getNodes().spliterator(), false).anyMatch(n -> n == b));
		Assert.assertFalse(StreamSupport.stream(graph2.getNodes().spliterator(), false).anyMatch(n -> n == c));

		graph1.addNode(d);
		Assert.assertEquals(2, StreamSupport.stream(graph1.getNodes().spliterator(), false).count());
		Assert.assertEquals(1, StreamSupport.stream(graph2.getNodes().spliterator(), false).count());

		graph2.addNode(e);
		Assert.assertEquals(2, StreamSupport.stream(graph2.getNodes().spliterator(), false).count());
		Assert.assertFalse(graph1.contains(e));
		Assert.assertFalse(graph2.contains(d));

		boolean exceptionThrown = false;
		try {
			graph1.addNode(null);
		} catch (NullPointerException ex) {
			exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
	}

	@Test
	public void testEdgeSet() {
		ExecutionGraph graph1 = new ExecutionGraph();
		graph1.addNode(a);
		graph1.addNode(b);
		graph1.addNode(c);
		graph1.addEdge(a, b);
		Assert.assertEquals(1, graph1.getEdges().size());
		Assert.assertTrue(graph1.containsEdge(a, b));
		Assert.assertFalse(graph1.containsEdge(b, a));
		Assert.assertFalse(graph1.containsEdge(a, a));

		graph1.addEdge(a, b);
		Assert.assertEquals(1, graph1.getEdges().size());
		Assert.assertTrue(graph1.containsEdge(a, b));
		Assert.assertFalse(graph1.containsEdge(b, a));
		Assert.assertFalse(graph1.containsEdge(a, a));

		graph1.addEdge(a, c);
		graph1.addEdge(b, c);
		Assert.assertEquals(3, graph1.getEdges().size());

		graph1.removeNode(a);
		Assert.assertEquals(1, graph1.getEdges().size());
		Assert.assertFalse(graph1.containsEdge(a, b));
		Assert.assertFalse(graph1.containsEdge(a, c));
		Assert.assertTrue(graph1.containsEdge(b, c));

		boolean exceptionThrown = false;
		try {
			graph1.addEdge(a, null);
		} catch (NullPointerException ex) {
			exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
	}
}
