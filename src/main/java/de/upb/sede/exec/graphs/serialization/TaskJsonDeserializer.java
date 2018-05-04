package de.upb.sede.exec.graphs.serialization;

import java.util.Map;

import de.upb.sede.exec.Execution;
import de.upb.sede.exec.Task;

/**
 * @author aminfaez
 *
 */
public class TaskJsonDeserializer {
	/**
	 * 
	 * Deserializes a node from the composition graph into a task object.
	 *
	 */
	public Task fromJSON(Execution execution, Map<String, Object> jsonData) {
		Task task = new Task(execution, (String) jsonData.get("nodetype"), jsonData);
		return task;
	}
}
