package de.upb.sede.webinterfaces.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.upb.sede.util.Streams;

/**
 * 
 * @author aminfaez
 * 
 * Interface used to abstract frameworks (e.g. http frameworks) for sending data.
 * 
 */
public interface BasicClientRequest {
	
	/**
	 * Send returns an outputstream.
	 * Writing into it should send the data.
	 * 
	 */
	public OutputStream send();
	
	/**
	 *  Returns an inputstream which contains the answer of the transaction.
	 *  Might block.
	 *  
	 */
	public InputStream receive();
	
	
	/**
	 * Wraps the send receive and send method by accepting string as payload, writing it into the send output-stream and returning the string of the receivestream as the answer. 
	 * @throws IOException 
	 */
	public default String send(String payload){
		/*
		 * send data
		 */
		 Streams.OutWriteString(send(), payload, true);
		 /*
		  * read answer
		  */
		 return Streams.InReadString(receive());
	}
	
}
