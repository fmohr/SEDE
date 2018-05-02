package de.upb.sede.exec.graphs.serialization;

import java.util.Map;

import de.upb.sede.exec.Execution;
import de.upb.sede.exec.Task;
import de.upb.sede.exec.graphs.EGraph;

/**
 * @author aminfaez
 *
 */
public class TaskJsonDeserializer {
	/**
	 * 
	 * Deserializes a node from the composition graph into a task object.
	 * 
	 * @param requestId
	 *            id of the request.
	 * @param jsonData
	 * @return
	 */
	public Task fromJSON(EGraph graph, Map<String, Object> jsonData) {
		Task task = new Task(graph, (String) jsonData.get("nodetype"), jsonData);
		return task;
	}
}
