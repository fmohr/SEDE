package de.upb.sede.interfaces;

import de.upb.sede.requests.Request;

public interface IExecutor {
	public void put(Request dataPutRequest);

	public void exec(Request execRequest);

	public void interrupt(Request interruptRequest);

	public void loadServices(Object Services);
}