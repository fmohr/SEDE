package de.upb.sede.exec;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.upb.sede.exceptions.CompositionGraphSerializationException;
import de.upb.sede.exec.Execution;
import de.upb.sede.exec.Task;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Contains methods to deserialize an execution graph from its JSON
 * representation.
 * 
 * @author aminfaez
 *
 */
public class GraphJsonDeserializer {

	private static final String JSON_FIELDNAME_NODES = "nodes";
	private static final String JSON_FIELDNAME_EDGES = "edges";


	/**
	 * Deserializes the given json string and creates tasks and connects them based on the edges in the graph. <p>
	 * All tasks are also added to the given execution graph.
	 *
	 * @param jsonStringGraph JSON-String representation of an execution graph. Needs to define
	 *            nodes and edges field.
	 * @param execution created tasks are added to this instance.
	 *
	 */
	public void deserializeTasksInto(Execution execution, String jsonStringGraph) {
		JSONParser jsonParser = new JSONParser();
		try {
			deserializeTasksInto(execution, (Map<Object, Object>) jsonParser.parse(jsonStringGraph));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates tasks and connects them based on the edges in the graph.
	 * All tasks are also added to the given execution graph.
	 * 
	 * @param jsonGraphObject
	 *            json object which is the serialization of an execution graph. Needs to define
	 *            nodes and edges field.
	 *
	 * @param execution created tasks are added to this instance.
	 */
	public void deserializeTasksInto(Execution execution, Map<Object, Object> jsonGraphObject) {
		if (!jsonGraphObject.containsKey(JSON_FIELDNAME_EDGES) || !jsonGraphObject.containsKey(JSON_FIELDNAME_NODES)) {
			throw new CompositionGraphSerializationException(
					"Cannot create a graph from a json object that doesn't contain fields: " + JSON_FIELDNAME_EDGES
							+ " and " + JSON_FIELDNAME_NODES);
		}

		List<Object> serializedNodes = (List<Object>) jsonGraphObject.get(JSON_FIELDNAME_NODES);
		Map<Object, Object> edgeMap = (Map<Object, Object>) jsonGraphObject.get(JSON_FIELDNAME_NODES);

		/*
		 * Deserialize tasks:
		 */
		List<Task> tasks = new ArrayList<>(serializedNodes.size()); // fill a map to hold indices of nodes.
		for (Object jsonNode : serializedNodes) {
			Map<String, Object> serializedNode = (Map<String, Object>) jsonNode;
			Task task = fromJSON(execution, serializedNode);
			tasks.add(task);
			execution.addTask(task);
		}
		/*
		 * connect tasks in the graph:
		 */
		for (Object edge : edgeMap.keySet()) {
			int sourceTaskIndex = Integer.parseInt(edge.toString()); // edge itself is the string representation of the
																		// source index

			List<Object> targetTaskIndices = (List<Object>) edgeMap.get(edge);
			for (Object targetNodeObject : targetTaskIndices) {
				Integer targetTaskIndex = (Integer) targetNodeObject;
				Task sourceTask = tasks.get(sourceTaskIndex);
				Task targetTask = tasks.get(targetTaskIndex);
				targetTask.getState().observe(sourceTask);
			}
		}
		for(Task t : tasks){
			t.updateDependendency();
		}
	}

	/**
	 *
	 * Deserializes a node from the composition graph into a task object.
	 *
	 */
	private Task fromJSON(Execution execution, Map<String, Object> jsonData) {
		Task task = new Task(execution, (String) jsonData.get("nodetype"), jsonData);
		return task;
	}

}
