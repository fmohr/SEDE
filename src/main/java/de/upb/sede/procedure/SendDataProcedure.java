package de.upb.sede.procedure;

import de.upb.sede.exec.SEDEObject;
import de.upb.sede.exec.Task;
import de.upb.sede.webinterfaces.client.BasicClientRequest;

public class SendDataProcedure implements Procedure {

	@Override
	public void process(Task task) {
		String fieldname = (String) task.getAttributes().get("fieldname");
		String targetaddress = (String) task.getAttributes().get("targetaddress");
		SEDEObject sedeObjectToSend = task.getExecutionGraph().getExecution().getExecutionEnvironment().get(fieldname);
		BasicClientRequest request = task.getExecutionGraph().getExecution().getClientRequest(targetaddress);

	}
}
