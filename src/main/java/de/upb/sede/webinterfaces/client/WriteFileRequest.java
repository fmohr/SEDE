package de.upb.sede.webinterfaces.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
/**
 * Implementation of basic client that writes the payload to a file located at destination.
 * @author aminfaez
 *
 */
public class WriteFileRequest implements BasicClientRequest {

	private final String filepath;
	private final byte[] answer;
	
	public WriteFileRequest(String filepath, String answer){
		this.filepath = filepath;
		this.answer = answer.getBytes();
	}

	@Override
	public OutputStream send() {
		File file = new File(filepath);
		/*
		 * Create directories up to the file. 
		 */
		file.getParentFile().mkdirs();
		
		/*
		 * return output stream
		 */
		try {
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public InputStream receive() {
		return new ByteArrayInputStream(answer); 
	}

}
