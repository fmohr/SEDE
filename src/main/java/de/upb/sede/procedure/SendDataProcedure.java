package de.upb.sede.procedure;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.Task;
import de.upb.sede.webinterfaces.client.BasicClientRequest;

public class SendDataProcedure extends Procedure {

	
	
	public SendDataProcedure(Task task) {
		super(task);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(Task task) {
		String fieldname = (String) task.getAttributes().get("fieldname");
		String targetaddress = (String) task.getAttributes().get("targetaddress");
		SEDEObject sedeObjectToSend = task.getExecution().getExecutionEnvironment().get(fieldname);
		BasicClientRequest request = task.getExecution().createClientRequest(targetaddress);

	}
}
