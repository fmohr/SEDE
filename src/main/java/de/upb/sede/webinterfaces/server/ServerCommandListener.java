package de.upb.sede.webinterfaces.server;

import de.upb.sede.util.Streams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.SystemPropertiesPropertySource;

import javax.swing.text.html.Option;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ServerCommandListener {

	private final static Logger logger = LogManager.getLogger();

	private final ImServer server;

	private final Map<String, Supplier<HTTPServerResponse>> commands = new ConcurrentHashMap<>();

	private boolean running = true;

	public ServerCommandListener(ImServer server) {
		this.server = server;
		addCommandHandle("shutdown", ShutdownServer::new);
	}

	public void addCommandHandle(String commandname, final Supplier<HTTPServerResponse> responder) {
		commands.put(commandname, responder);
		server.addHandle("/" + commandname, responder);
	}


	public void addCommandHandle(String commandname, final Function<List<String>, String> command) {
		this.addCommandHandle(commandname, () -> new ServerCommandHandle(command));
	}

	public void listenEndlessly() {
		while(running) {
			listenToStandardIn();
		}
	}

	private void listenToStandardIn() {
		printCommands();
		String input  = Streams.InReadLine(System.in);
		logger.info("Processing console command: {}", input);
		List<String> inputItems = new ArrayList<>(Arrays.asList(input.split(" ")));
		if(!inputItems.isEmpty()) {
			String commandname = inputItems.get(0);
			if(commands.containsKey(commandname)) {
				input = "/" + input.replaceAll(" ", "/");
				ByteArrayInputStream in = new ByteArrayInputStream("".getBytes());
				commands.get(commandname).get().receive(Optional.of(input), in, System.out);
				System.out.println();
			}
		}
	}

	private void printCommands() {
		String commandList = "\n\t" + commands.keySet().stream().collect(Collectors.joining("\n\t"));
		System.out.println("Commands are: " + commandList);
	}


	static class ServerCommandHandle implements HTTPServerResponse {

		private final Function<List<String>, String> command;

		ServerCommandHandle(Function<List<String>, String> command) {
			this.command = command;
		}

		@Override
		public void receive(Optional<String> url, InputStream payload, OutputStream answer) {
			Streams.InReadString(payload);
			String returnMessage;
			if(url.isPresent()) {
				logger.info("Processing server command. url: {}", url.get());
				List<String> inputItems = new ArrayList<>(Arrays.asList(url.get().split("/")));
				if(inputItems.size() < 2) {
					returnMessage = "url contains no command: " + url;
				} else {
					inputItems.remove(0); // first one is empty.
					inputItems.remove(0); // the second one is the command name
					returnMessage = command.apply(inputItems);
				}
			} else {
				returnMessage = "url is empty.";
			}
			Streams.OutWriteString(answer, returnMessage, true);
		}
	}
	class ShutdownServer implements HTTPServerResponse {

		@Override
		public void receive(Optional<String> url, InputStream payload, OutputStream answer) {
			Streams.OutWriteString(answer, "Server is shutting down...", true);
			server.shutdown();
			running = false;
			System.exit(0);
		}
	}
}
