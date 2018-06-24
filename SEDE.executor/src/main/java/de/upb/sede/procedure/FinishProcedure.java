package de.upb.sede.procedure;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.SemanticStreamer;
import de.upb.sede.exec.Task;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.client.BasicClientRequest;

import java.io.IOException;
import java.io.UncheckedIOException;

public abstract class FinishProcedure implements Procedure {
	@Override
	public void process(Task task) {
		finishNotice(task);
	}

	@Override
	public void processFail(Task task) {
		finishNotice(task);
	}

	private void finishNotice(Task task) {
		Boolean executionSuccess = !task.hasFailed();
		SEDEObject successObject = new SEDEObject(SEDEObject.PrimitiveType.Bool.name(), executionSuccess);
		try(BasicClientRequest finishFlagRequest = getFinishFlagRequest(task)) {
			SemanticStreamer.streamInto(finishFlagRequest.send(), successObject);
			String result = Streams.InReadString(finishFlagRequest.receive());
			if(!result.isEmpty()) {
				throw new RuntimeException("Finish notice to client failed. Answer was not empty: " + result);
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		task.setSucceeded();
	}


	/**
	 * Returns a BasicClientReuqest to send the notice.
	 * @return A BasicClientRequest to use for communication.
	 */
	public abstract BasicClientRequest getFinishFlagRequest(Task task);

}
