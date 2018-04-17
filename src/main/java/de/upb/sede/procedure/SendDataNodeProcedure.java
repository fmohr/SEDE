package de.upb.sede.procedure;

import de.upb.sede.exec.SEDEObject;
import de.upb.sede.exec.Task;

public class SendDataNodeProcedure implements Procedure {

	@Override
	public Object process(Task task) {
		String fieldname = (String) task.getAttributes().get("fieldname");
		String targetaddress = (String) task.getAttributes().get("targetaddress");
		SEDEObject sedeObjectToSend = task.getExecution().getExecutionEnvironment().get(fieldname);
		
		
	}
}
