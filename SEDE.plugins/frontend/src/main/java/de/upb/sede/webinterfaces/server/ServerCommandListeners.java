package de.upb.sede.webinterfaces.server;

import de.upb.sede.util.Streams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ServerCommandListeners {

	private final static Logger logger = LogManager.getLogger();

	private final Set<CommandListener> listeners = new HashSet<>();

	public void addListener(CommandListener cl) {
		listeners.add(Objects.requireNonNull(cl));
	}

	public void addCommandHandle(final CommandTree responder) {
		for (CommandListener cl : this.listeners) {
			cl.listenToCommands(responder);
		}
	}

	public void addCommandHandle(Command command, final Function<List<String>, String> handle) {
		addCommandHandle(new CommandTree(CommandTree.node(command.addExe(matches->
				handle.apply(CommandTree.toStrList(matches))))));
	}


 	@Deprecated
	public void listenEndlessly() {
	}

	@Deprecated
	private void listenToStandardIn() {
		System.out.print("Enter command: ");
		String input  = Streams.InReadLine(System.in);
		List<String> inputItems = new ArrayList<>(Arrays.asList(input.split(" ")));
		if(!inputItems.isEmpty()) {
			String commandname = inputItems.get(0);
//			if(commands.containsKey(commandname)) {
//				input = "/" + input.replaceAll(" ", "/"); // convert input list to url.
//				ByteArrayOutputStream out = new ByteArrayOutputStream();
//				HTTPServerResponse responder = commands.get(commandname).get();
//				responder.receive(Optional.of(input), Streams.EmptyInStream(), out);
//				System.out.println(out.toString());
//			}
		}
	}

	@Deprecated
	static class ServerCommandHandle implements HTTPServerResponse {

		private final Function<List<String>, String> handle;

		ServerCommandHandle(Function<List<String>, String> handle) {
			this.handle = handle;
		}

		@Override
		public void receive(Optional<String> url, InputStream payload, OutputStream answer) {
			Streams.InReadString(payload);
			String returnMessage;
			if(url.isPresent()) {
				logger.info("Processing handle. url: {}", url.get());
				List<String> inputItems = new ArrayList<>(Arrays.asList(url.get().split("/")));
				if(inputItems.size() < 2) {
					returnMessage = "url contains no handle: " + url;
				} else {
					inputItems.remove(0); // first one is empty.
					inputItems.remove(0); // the second one is the handle name
					logger.trace("Delegating list to handle: {}", inputItems);
					returnMessage = handle.apply(inputItems);
				}
			} else {
				returnMessage = "url is empty.";
			}
			Streams.OutWriteString(answer, returnMessage, true);
		}
	}
//	class ShutdownServer implements HTTPServerResponse {
//
//		@Override
//		public void receive(Optional<String> url, InputStream payload, OutputStream answer) {
//			Streams.OutWriteString(answer, "Server is shutting down...", true);
//			server.shutdown();
//			running = false;
//			System.exit(0);
//		}
//	}
//
//	class CommandPrinter implements HTTPServerResponse {
//		@Override
//		public void receive(Optional<String> url, InputStream payload, OutputStream answer) {
//			String commandList = "\n\t" + commands.keySet().stream().sorted().collect(Collectors.joining("\n\t"));
//			Streams.OutWriteString(answer, commandList, true);
//		}
//	}
}
