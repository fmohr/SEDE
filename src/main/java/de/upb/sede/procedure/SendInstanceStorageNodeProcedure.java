package de.upb.sede.procedure;

import de.upb.sede.exec.Task;

public class SendInstanceStorageNodeProcedure implements Procedure{

	@Override
	public Object process(Task task) {
		String serviceinstanceFieldname = (String) task.getParameters().get("serviceinstanceFieldname");
		boolean hasId = (boolean) task.getParameters().get("hasId");
		boolean isLoadInstruction = (boolean) task.getParameters().get("isLoadInstruction");
		String serviceClasspath = (String) task.getParameters().get("serviceClasspath");
		String id = (String) task.getParameters().get("id");
		return null;
	}
}
