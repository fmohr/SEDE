package de.upb.sede.webinterfaces.server;

import de.upb.sede.util.FileUtil;

import static de.upb.sede.webinterfaces.server.CommandTree.*;
import static de.upb.sede.webinterfaces.server.Command.*;

public class StdShellCommands {

	public static void enablePlugin(ServerCommandListeners scl) {
		CommandTree cat = new CommandTree(
			node(new Strings("cat"),
					node(new File(".", false, true)
							.addExe(t ->
									FileUtil.readFileAsString(CommandTree.lastMatch(t))
							)))
		);
		scl.addCommandHandle(cat);
	}
}
