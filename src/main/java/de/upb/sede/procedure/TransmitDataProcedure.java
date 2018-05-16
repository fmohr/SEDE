package de.upb.sede.procedure;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.SemanticStreamer;
import de.upb.sede.exec.Task;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.client.BasicClientRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

public abstract class TransmitDataProcedure implements Procedure{

	@Override
	public void process(Task task) {
		String fieldname = (String) task.getAttributes().get("fieldname");
		SEDEObject sedeObjectToSend = task.getExecution().getExecutionEnvironment().get(fieldname);


		try (BasicClientRequest putDataRequest = getPutDataRequest(task)){
			OutputStream outputStream = putDataRequest.send();
			if(sedeObjectToSend.isReal()){
				String casterClasspath = (String) task.getAttributes().get("caster");
				String semanticType = (String) task.getAttributes().get("semantic-type");

				SemanticStreamer.streamObjectInto(outputStream, sedeObjectToSend, casterClasspath, semanticType);
			} else {
				SemanticStreamer.streamInto(outputStream, sedeObjectToSend);
			}
			String response = Streams.InReadString(putDataRequest.receive());
			if(!response.isEmpty()) {
				throw new RuntimeException("Error transmitting data:\n\t" + sedeObjectToSend.toString() +
						"\nto:\n\t" + task.getAttributes().get("contact-info").toString()+ "\nReturned message is not empty:\n\t" + response);
			} else {
				task.setSucceeded();
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public abstract BasicClientRequest getPutDataRequest(Task task);

}
