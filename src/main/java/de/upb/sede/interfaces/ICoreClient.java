package de.upb.sede.interfaces;

import de.upb.sede.requests.Request;

public interface ICoreClient {
	public void run(Request runRequest);

	public void receive(Object result);
}