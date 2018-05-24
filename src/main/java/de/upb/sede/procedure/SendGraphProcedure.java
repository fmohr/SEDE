package de.upb.sede.procedure;

import de.upb.sede.exec.Task;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.webinterfaces.client.BasicClientRequest;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Procedure to send a graph to some other executor.
 */
public abstract class SendGraphProcedure implements Procedure {
	@Override
	public void process(Task task) {
		String graph = (String) task.getAttributes().get("graph");
		String executionId = task.getExecution().getExecutionId();
		ExecRequest request = new ExecRequest(executionId, graph);

		try (BasicClientRequest execRequest = getExecRequest(task)) {
			String response = execRequest.send(request.toJsonString());

			if (!response.isEmpty()) {
				throw new RuntimeException(
						"Error sending graph to:\n\t" + task.getAttributes().get("contact-info").toString()
								+ "\nReturned message is not empty:\n\t" + response);
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		task.setSucceeded();
	}

	/**
	 * Returns a BasicClientReuqest to send the graph over and get a response. The
	 * implementation is dependent on the used framework to send data.
	 * 
	 * @param task
	 *            Task to create a BasicClientRequest for.
	 * @return The BasicClientReuqest to use for communication
	 */
	public abstract BasicClientRequest getExecRequest(Task task);
}
