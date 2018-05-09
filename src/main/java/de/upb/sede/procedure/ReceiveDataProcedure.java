package de.upb.sede.procedure;

import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.Task;

public class ReceiveDataProcedure extends Procedure {

	
	
	public ReceiveDataProcedure(Task task) {
		super(task);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(Task task) {
		String fieldname = (String) task.getAttributes().get("fieldname");
		ExecutionEnvironment environment = task.getExecution().getExecutionEnvironment();
	}
}
