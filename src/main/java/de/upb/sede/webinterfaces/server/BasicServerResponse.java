package de.upb.sede.webinterfaces.server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * A handler which is invoked to process server-side exchanges. 
 * 
 * @author aminfaez
 *
 */
public interface BasicServerResponse {
	/**
	 * 
	 * @param payload data from client
	 * @param answer data sent back to client
	 */
	public void receive(InputStream payload, OutputStream answer);
}
