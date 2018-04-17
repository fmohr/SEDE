package de.upb.sede.procedure;

import de.upb.sede.exec.Task;

public class SendInstanceStorageNodeProcedure implements Procedure{

	@Override
	public Object process(Task task) {
		String serviceinstanceFieldname = (String) task.getAttributes().get("serviceinstanceFieldname");
		boolean hasId = (boolean) task.getAttributes().get("hasId");
		boolean isLoadInstruction = (boolean) task.getAttributes().get("isLoadInstruction");
		String serviceClasspath = (String) task.getAttributes().get("serviceClasspath");
		String id = (String) task.getAttributes().get("id");
		return null;
	}
}
