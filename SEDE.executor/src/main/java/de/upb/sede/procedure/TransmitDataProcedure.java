package de.upb.sede.procedure;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.SemanticStreamer;
import de.upb.sede.exec.Task;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.client.BasicClientRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

/**
 * Procedure to transmit data.
 */
public abstract class TransmitDataProcedure implements Procedure{

	@Override
	public void process(Task task) {
		String fieldname = (String) task.getAttributes().get("fieldname");
		SEDEObject sedeObjectToSend = task.getExecution().getEnvironment().get(fieldname);


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

	public void processFail(Task task) {
		try (BasicClientRequest dataUnavailableNotice = getPutDataRequest(task)){
			String response = dataUnavailableNotice.send(""); // TODO what do we put in the body?
			if(!response.isEmpty()) {
				throw new RuntimeException("Error giving unavailability notice to:\n\t" + task.getAttributes().get("contact-info").toString()+
						"\nReturned message is not empty:\n\t" + response);
			}
		}
		catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	 /** 
	  * Returns a BasicClientReuqest to send the data over and get a response. The
	 * implementation is dependent on the used framework to send data.
	  * @param task Task that demands a data transmission.
	  * @return A BasicClientRequest to use for communication.
	  */
	public abstract BasicClientRequest getPutDataRequest(Task task);

}
