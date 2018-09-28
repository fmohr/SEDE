package de.upb.sede.webinterfaces.server;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.util.Streams;

public class ImServerCommandListener implements CommandListener {

	private final static Logger logger = LoggerFactory.getLogger(ImServerCommandListener.class);
	private final ImServer innerServer;
	private final CommandTree commandResponders = new CommandTree(CommandTree.node(Command.nothing()));

	public ImServerCommandListener(ImServer innerServer) {
		this.innerServer = innerServer;
		innerServer.addHandle("/cmd", GETResponse::new);
	}

	public void listenToCommands(CommandTree responder) {
		commandResponders.addOptionToRoot(responder);
	}

	private class GETResponse extends StringServerResponse {
		@Override
		public String receive(Optional<String> url, String payload){
			if(!url.isPresent()) {
				return "PROVIDE URL";
			}
			String urlString = url.get();
			logger.debug("Command received {}.", urlString);
			if(!urlString.startsWith("/cmd/")){
				return "URL MISMATCH";
			}
			String splits[] = urlString.substring("/cmd/".length()).split("/");
			try{
				return commandResponders.execute(splits);
			} catch(CommandFormatMismatch ex) {
				return "404\nUnrecognised command: " + Arrays.asList(splits);
			} catch (Exception ex) {
				return "Couldnt process command:\n\n" + Streams.ErrToString(ex);
			}
		}

		@Override
		public String receive(String payload) {
			return "PROVIDE URL";
		}
	}

}
