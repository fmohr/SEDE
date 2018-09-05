package de.upb.sede.webinterfaces.server;

import java.util.function.Supplier;

public interface CommandListener {
	void listenToCommands(final CommandTree responder);

}
