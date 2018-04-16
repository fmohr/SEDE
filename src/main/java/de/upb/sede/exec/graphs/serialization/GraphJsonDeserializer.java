package de.upb.sede.exec.graphs.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.upb.sede.composition.graphs.Edge;
import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.GraphTraversal;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.exceptions.CompositionGraphSerializationException;
import de.upb.sede.exec.Execution;
import de.upb.sede.exec.ExecutionGraph;
import de.upb.sede.exec.graphs.EGraph;
import de.upb.sede.exec.Task;
import de.upb.sede.util.Iterators;

/**
 * Contains methods to deserialize an execution graph from its JSON representation.
 * @author aminfaez
 *
 */
public class GraphJsonDeserializer {

	private static final String JSON_FIELDNAME_NODES = "nodes";
	private static final String JSON_FIELDNAME_EDGES = "edges";

	/**
	 * Serializes the given graph to JSON.
	 * @param graph an arbitrary graph
	 * @return JSON representation of the graph.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJson(ExecutionGraph graph) {
		return null; // TODO do we need this?
	}

	/**
	 * Deserializes the given json map into an execution graph. 
	 * 
	 * @param jsonGraphObject json object which is the serialization of a graph. Needs to define nodes and edges field.
	 * @return The deserialized execution graph
	 */
	@SuppressWarnings("unchecked")
	public ExecutionGraph fromJson(Execution execution, Map<Object, Object> jsonGraphObject) {
		if (!jsonGraphObject.containsKey(JSON_FIELDNAME_EDGES) || !jsonGraphObject.containsKey(JSON_FIELDNAME_NODES)) {
			throw new CompositionGraphSerializationException(
					"Cannot create a graph from a json object that doesn't contain fields: " + JSON_FIELDNAME_EDGES
							+ " and " + JSON_FIELDNAME_NODES);
		}
		EGraph deserializedGraph = new EGraph();
		List<Object> serializedNodes = (List<Object>) jsonGraphObject.get(JSON_FIELDNAME_NODES);
		Map<Object, Object> edgeMap = (Map<Object, Object>) jsonGraphObject.get(JSON_FIELDNAME_NODES);
		
		/*
		 * Deserialize nodes:
		 */
		TaskJsonDeserializer tjs = new TaskJsonDeserializer();
		List<Task> orderOfTasks = new ArrayList<>(serializedNodes.size()); // fill a map to hold indices of nodes. 
		for(Object jsonNode : serializedNodes) {
			Map<String, Object> serializedNode = (Map<String, Object>) jsonNode;
			Task task = tjs.fromJSON(execution, serializedNode);
			orderOfTasks.add(task);
			deserializedGraph.addTask(task);
		}
		/*
		 * connect nodes in the graph:
		 */
		for(Object edge : edgeMap.keySet()) {
			int sourceNodeIndex = Integer.parseInt(edge.toString()); // edge itself is the string representation of the source index
			
			List<Object> targetNodeIndices = (List<Object>) edgeMap.get(edge);
			for(Object targetNodeObject : targetNodeIndices) {
				Integer targetNodeIndex = (Integer) targetNodeObject;
				deserializedGraph.connectTasks(orderOfTasks.get(sourceNodeIndex), orderOfTasks.get(targetNodeIndex));
			}
		}
		return deserializedGraph;
	}

}
