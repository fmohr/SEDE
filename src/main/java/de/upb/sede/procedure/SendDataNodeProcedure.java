package de.upb.sede.procedure;

import de.upb.sede.exec.SEDEObject;
import de.upb.sede.exec.Task;
import de.upb.sede.webinterfaces.WriteFileRequest;

public class SendDataNodeProcedure implements Procedure {

	@Override
	public Object process(Task task) {
		String fieldname = (String) task.getParameters().get("fieldname");
		String targetaddress = (String) task.getParameters().get("targetaddress");
		SEDEObject sedeObjectToSend = task.getExecution().getExecutionEnvironment().get(fieldname);

		String response = "";
		WriteFileRequest writeFileRequest = new WriteFileRequest(targetaddress, response);
		writeFileRequest.send(sedeObjectToSend.toJSON());
		return response;
	}
}
