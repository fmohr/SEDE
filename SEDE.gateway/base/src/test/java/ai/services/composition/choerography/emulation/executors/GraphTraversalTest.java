package ai.services.composition.choerography.emulation.executors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ai.services.composition.graphs.DummyNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ai.services.composition.graphs.nodes.BaseNode;

public class GraphTraversalTest {

	ExecutionGraph graph1 = new ExecutionGraph();

	DummyNode a = new DummyNode("a");
	DummyNode b = new DummyNode("b");
	DummyNode c = new DummyNode("c");
	DummyNode d = new DummyNode("d");
	DummyNode e = new DummyNode("e");
	DummyNode f = new DummyNode("f");
	DummyNode g = new DummyNode("g");

	@Before
	public void createGraph() {
		graph1 = new ExecutionGraph();

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
			graph1.addEdge(a, b);
			graph1.addEdge(a, c);
			graph1.addEdge(c, d);
			graph1.addEdge(c, e);
			graph1.addEdge(d, f);
			graph1.addEdge(g, c);
		}
	}

	@Test
	public void testBFS() {
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

	@Test
	public void testTopologicalSort() {
		Iterator<BaseNode> topologicalSortIterator = GraphTraversal.topologicalSort(graph1).iterator();
		List<BaseNode> layer = new ArrayList<BaseNode>();

		/*
		 * Compare iteration layer by layer:
		 */
		layer.add(topologicalSortIterator.next());
		layer.add(topologicalSortIterator.next());
		Assert.assertTrue(layer.contains(a));
		Assert.assertTrue(layer.contains(g));

		// next layer
		layer.clear();
		layer.add(topologicalSortIterator.next());
		layer.add(topologicalSortIterator.next());
		Assert.assertTrue(layer.contains(b));
		Assert.assertTrue(layer.contains(c));

		// next layer
		layer.clear();
		layer.add(topologicalSortIterator.next());
		layer.add(topologicalSortIterator.next());
		Assert.assertTrue(layer.contains(d));
		Assert.assertTrue(layer.contains(e));
	}

	@Test
	public void testNeighbors() {
		List<BaseNode> neighbors = new ArrayList<>();
		// Next neighborhood
		GraphTraversal.targetingNodes(graph1, a).forEach(node -> neighbors.add(node));
		Assert.assertTrue(neighbors.size() == 2);
		Assert.assertTrue(neighbors.contains(b));
		Assert.assertTrue(neighbors.contains(c));
		// Next neighborhood
		neighbors.clear();
		GraphTraversal.targetingNodes(graph1, b).forEach(node -> neighbors.add(node));
		Assert.assertTrue(neighbors.size() == 0);
		// Next neighborhood
		neighbors.clear();
		GraphTraversal.targetingNodes(graph1, c).forEach(node -> neighbors.add(node));
		Assert.assertTrue(neighbors.size() == 2);
		Assert.assertTrue(neighbors.contains(d));
		Assert.assertTrue(neighbors.contains(e));
		// Next neighborhood
		neighbors.clear();
		GraphTraversal.targetingNodes(graph1, d).forEach(node -> neighbors.add(node));
		Assert.assertTrue(neighbors.size() == 1);
		Assert.assertTrue(neighbors.contains(f));
		// Next neighborhood
		neighbors.clear();
		GraphTraversal.targetingNodes(graph1, e).forEach(node -> neighbors.add(node));
		Assert.assertTrue(neighbors.size() == 0);
		// Next neighborhood
		neighbors.clear();
		GraphTraversal.targetingNodes(graph1, f).forEach(node -> neighbors.add(node));
		Assert.assertTrue(neighbors.size() == 0);
		// Next neighborhood
		neighbors.clear();
		GraphTraversal.targetingNodes(graph1, g).forEach(node -> neighbors.add(node));
		Assert.assertTrue(neighbors.size() == 1);
		Assert.assertTrue(neighbors.contains(c));
	}

	@Test
	public void testAllEdgesWith() {
		List<ExecutionGraph.GraphEdge> allEdgesWith = new ArrayList<>();
		// Incidence of next node
		GraphTraversal.allEdgesWith(graph1, a).forEach(edge -> allEdgesWith.add(edge));
		Assert.assertTrue(allEdgesWith.size() == 2);
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(a, b)));
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(a, c)));
		// Incidence of next node
		allEdgesWith.clear();
		GraphTraversal.allEdgesWith(graph1, b).forEach(edge -> allEdgesWith.add(edge));
		Assert.assertTrue(allEdgesWith.size() == 1);
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(a, b)));
		// Incidence of next node
		allEdgesWith.clear();
		GraphTraversal.allEdgesWith(graph1, c).forEach(edge -> allEdgesWith.add(edge));
		Assert.assertTrue(allEdgesWith.size() == 4);
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(a, c)));
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(g, c)));
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(c, d)));
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(c, e)));
		// Incidence of next node
		allEdgesWith.clear();
		GraphTraversal.allEdgesWith(graph1, d).forEach(edge -> allEdgesWith.add(edge));
		Assert.assertTrue(allEdgesWith.size() == 2);
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(c, d)));
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(d, f)));
		// Incidence of next node
		allEdgesWith.clear();
		GraphTraversal.allEdgesWith(graph1, e).forEach(edge -> allEdgesWith.add(edge));
		Assert.assertTrue(allEdgesWith.size() == 1);
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(c, e)));
		// Incidence of next node
		allEdgesWith.clear();
		GraphTraversal.allEdgesWith(graph1, f).forEach(edge -> allEdgesWith.add(edge));
		Assert.assertTrue(allEdgesWith.size() == 1);
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(d, f)));
		// Incidence of next node
		allEdgesWith.clear();
		GraphTraversal.allEdgesWith(graph1, g).forEach(edge -> allEdgesWith.add(edge));
		Assert.assertTrue(allEdgesWith.size() == 1);
		Assert.assertTrue(allEdgesWith.contains(new ExecutionGraph.GraphEdge(g, c)));
	}

	@Test
	public void testIsTherePathFromTo() {
		// Checking paths for a
		Assert.assertTrue(GraphTraversal.isTherePathFromTo(graph1, a, b));
		Assert.assertTrue(GraphTraversal.isTherePathFromTo(graph1, a, c));
		Assert.assertTrue(GraphTraversal.isTherePathFromTo(graph1, a, d));
		Assert.assertTrue(GraphTraversal.isTherePathFromTo(graph1, a, e));
		Assert.assertTrue(GraphTraversal.isTherePathFromTo(graph1, a, f));
		Assert.assertFalse(GraphTraversal.isTherePathFromTo(graph1, a, g));
		// Checking paths for c
		Assert.assertFalse(GraphTraversal.isTherePathFromTo(graph1, c, a));
		Assert.assertFalse(GraphTraversal.isTherePathFromTo(graph1, c, b));
		Assert.assertTrue(GraphTraversal.isTherePathFromTo(graph1, c, d));
		Assert.assertTrue(GraphTraversal.isTherePathFromTo(graph1, c, e));
		Assert.assertTrue(GraphTraversal.isTherePathFromTo(graph1, c, f));
		Assert.assertFalse(GraphTraversal.isTherePathFromTo(graph1, c, g));
	}

	public void testIndependence() {
		Assert.assertTrue(GraphTraversal.isIndependent(graph1, a));
		Assert.assertFalse(GraphTraversal.isIndependent(graph1, b));
		Assert.assertFalse(GraphTraversal.isIndependent(graph1, c));
		Assert.assertFalse(GraphTraversal.isIndependent(graph1, d));
		Assert.assertFalse(GraphTraversal.isIndependent(graph1, e));
		Assert.assertFalse(GraphTraversal.isIndependent(graph1, f));
		Assert.assertTrue(GraphTraversal.isIndependent(graph1, g));
	}

	public void testIndependentNodes() {
		List<BaseNode> independentNodes = new ArrayList<>();
		GraphTraversal.independentNodes(graph1).forEach(node -> independentNodes.add(node));
		Assert.assertTrue(independentNodes.size() == 2);
		Assert.assertTrue(independentNodes.contains(a));
		Assert.assertTrue(independentNodes.contains(g));
	}
}
