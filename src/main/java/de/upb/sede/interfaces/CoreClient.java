package de.upb.sede.interfaces;

import de.upb.sede.requests.Request;

public interface CoreClient {
	public void run(Request runRequest);

	public void receive(Object result);
}