package de.upb.sede.webinterfaces;

import java.io.InputStream;
import java.io.OutputStream;

public interface BasicServerResponse {
	/**
	 * 
	 * @param payload data from client
	 * @param answer data sent back to client
	 */
	public void receive(InputStream payload, OutputStream answer);
}
