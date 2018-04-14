package de.upb.sede.composition.graphs.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.upb.sede.composition.graphs.Edge;
import de.upb.sede.composition.graphs.Graph;
import de.upb.sede.composition.graphs.GraphTraversal;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.exceptions.CompositionGraphSerializationException;
import de.upb.sede.util.Iterators;

/**
 * Contains methods to serialize a graph into and from its JSON representation.
 * @author aminfaez
 *
 */
public class GraphJsonSerializer {

	private static final String JSON_FIELDNAME_NODES = "nodes";
	private static final String JSON_FIELDNAME_EDGES = "edges";

	/**
	 * Serializes the given graph to JSON.
	 * @param graph an arbitrary graph
	 * @return JSON representation of the graph.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJson(Graph graph) {
		/*
		 * create a json array of nodes.
		 * 
		 * The order of the nodes can be arbitrary but the order needs to be immutable
		 * after it was defined because the edges work with indices defined by this
		 * array.
		 */
		List<BaseNode> orderOfNodes = Iterators.TO_LIST(GraphTraversal.iterateNodes(graph).iterator());
		JSONArray nodearray = new JSONArray();
		NodeJsonSerializer njs = new NodeJsonSerializer();
		for (BaseNode bn : orderOfNodes) {
			nodearray.add(njs.toJSON(bn));
		}
		/*
		 * serialize the edges by creating an "m":n tuple for each edge:
		 * 
		 * edges maps the m'th node to the n'th node. A mapping between two nodes, "m":
		 * n, means that the m'th node has an edge to the n'th node.
		 * 
		 * Indexes are defined by orderOfNodes list from above.
		 */
		JSONObject edges = new JSONObject();
		for (Edge edge : GraphTraversal.iterateEdges(graph)) {
			/*
			 * mapping: "m" : n
			 */
			int m = orderOfNodes.indexOf(edge.getFrom());
			int n = orderOfNodes.indexOf(edge.getTo());
			edges.put(String.valueOf(m), n);
		}
		JSONObject jsonGraphObject = new JSONObject();
		jsonGraphObject.put(JSON_FIELDNAME_NODES, nodearray);
		jsonGraphObject.put(JSON_FIELDNAME_EDGES, edges);
		return jsonGraphObject;
	}

	/**
	 * Deserializes the given json map into a graph. 
	 * 
	 * @param jsonGraphObject json object which is the serialization of a graph. Needs to define nodes and edges field.
	 * @return The deserialized graph
	 */
	@SuppressWarnings("unchecked")
	public Graph fromJson(Map<Object, Object> jsonGraphObject) {
		if (!jsonGraphObject.containsKey(JSON_FIELDNAME_EDGES) || !jsonGraphObject.containsKey(JSON_FIELDNAME_NODES)) {
			throw new CompositionGraphSerializationException(
					"Cannot create a graph from a json object that doesn't contain fields: " + JSON_FIELDNAME_EDGES
							+ " and " + JSON_FIELDNAME_NODES);
		}
		Graph deserializedGraph = new Graph();
		List<Object> serializedNodes = (List<Object>) jsonGraphObject.get(JSON_FIELDNAME_NODES);
		Map<Object, Object> edgeMap = (Map<Object, Object>) jsonGraphObject.get(JSON_FIELDNAME_NODES);
		
		/*
		 * Deserialize nodes:
		 */
		NodeJsonSerializer njs = new NodeJsonSerializer();
		List<BaseNode> orderOfNodes = new ArrayList<>(serializedNodes.size()); // fill a map to hold indices of nodes. 
		for(Object jsonNode : serializedNodes) {
			Map<Object, Object> serializedNode = (Map<Object, Object>) jsonNode;
			BaseNode bn = njs.fromJSON(serializedNode);
			orderOfNodes.add(bn);
			deserializedGraph.addNode(bn);
		}
		/*
		 * connect nodes in the graph:
		 * edgemap defines values like: 
		 * 		"m" : n
		 * 		m is the index of the source node and n is the target node of the edge.
		 */
		for(Object edge : edgeMap.keySet()) {
			int sourceNodeIndex = Integer.parseInt(edge.toString()); // edge itself is the string representation of the source index
			int targetNodeIndex = (Integer) edgeMap.get(edge);
			deserializedGraph.connectNodes(orderOfNodes.get(sourceNodeIndex), orderOfNodes.get(targetNodeIndex));
		}

		return deserializedGraph;
	}

}
