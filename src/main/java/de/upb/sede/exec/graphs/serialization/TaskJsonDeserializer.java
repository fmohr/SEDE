package de.upb.sede.exec.graphs.serialization;

import java.util.Map;

import de.upb.sede.exec.graphs.Task;


/**
 * @author aminfaez
 *
 */
public class TaskJsonDeserializer {
	/**
	 * 
	 * Deserializes a node from the composition graph into a task object.
	 * @param requestId id of the request.
	 * @param jsonData
	 * @return
	 */
	public Task fromJSON(String requestId, Map<String, Object> jsonData) {
		Task task = new Task(requestId, (String) jsonData.get("nodetype"), jsonData);
		return task;
	}
}
