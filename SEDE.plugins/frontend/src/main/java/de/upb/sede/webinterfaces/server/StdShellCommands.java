package de.upb.sede.webinterfaces.server;

import de.upb.sede.util.FileUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

import static de.upb.sede.webinterfaces.server.CommandTree.*;
import static de.upb.sede.webinterfaces.server.Command.*;

public class StdShellCommands {

	public static void enablePlugin(ServerCommandListeners scl) {
		CommandTree cat = new CommandTree(
			node(new Strings("cat"),
					node(new File(".", false, true)
							.addExe(t ->
									{
										String filePath =
												CommandTree.lastMatch(t) + "/"
										+ Arrays.stream(CommandTree.rest(t)).collect(Collectors.joining("/"));
										return FileUtil.readFileAsString(filePath);
									}

							)))
		);
		scl.addCommandHandle(cat);
	}
}
