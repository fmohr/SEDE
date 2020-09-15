package ai.services.webinterfaces.client;

import java.io.*;

import ai.services.util.Streams;

/**
 *
 * @author aminfaez
 *
 *         Interface used to abstract frameworks (e.g. http frameworks) for
 *         sending data.
 *
 */
public interface BasicClientRequest extends Closeable{

	/**
	 * Send returns an outputstream. Writing into it should send the data.
	 *
	 */
	public OutputStream send();

	/**
	 * Returns an inputstream which contains the answer of the transaction. Might
	 * block.
	 *
	 */
	public InputStream receive();

	/**
	 * Wraps the send receive and send method by accepting string as payload,
	 * writing it into the send output-stream and returning the string of the
	 * receivestream as the answer.
	 */
	public default String send(String payload) {
		/*
		 * send data
		 */
		Streams.OutWriteString(send(), payload, true);
		/*
		 * read answer
		 */
		String answer = Streams.InReadString(receive());
		/*
		 * close the request
		 */
		try {
			close();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return answer;
	}

}
