package de.upb.sede.webinterfaces;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

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
	 * Wraps the send receive method by accepting string as payload and returning a string as the answer. 
	 * @throws IOException 
	 */
	public default String send(String payload){
		
		
		/*
		 * send data
		 */
		 
		 try(OutputStream out = send()) {
			 out.write(payload.getBytes(StandardCharsets.UTF_8.name()));
		 } catch (UnsupportedEncodingException e) {
			 throw new RuntimeException(e);
		 } catch (IOException e) {
			 throw new UncheckedIOException(e);
		 }
		 
		 /*
		 * Convert answer stream to string and return
		 */
		try (InputStream streamAnswer = receive()){
		
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = streamAnswer.read(buffer)) != -1) {
			    result.write(buffer, 0, length);
			}
			return result.toString(StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
}
