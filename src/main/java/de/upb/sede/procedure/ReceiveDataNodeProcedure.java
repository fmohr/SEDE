package de.upb.sede.procedure;

import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.Task;

public class ReceiveDataNodeProcedure implements Procedure {

	@Override
	public Object process(Task task) {
		String fieldname = (String) task.getParameters().get("fieldname");
		ExecutionEnvironment environment = task.getExecution().getExecutionEnvironment();
		return (environment.containsKey(fieldname));
	}
}
