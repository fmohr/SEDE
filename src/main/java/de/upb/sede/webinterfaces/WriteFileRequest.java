package de.upb.sede.webinterfaces;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	private final String answer;
	
	public WriteFileRequest(String filepath, String answer){
		this.filepath = filepath;
		this.answer = answer;
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
		return new ByteArrayInputStream(answer.getBytes()); 
	}

}