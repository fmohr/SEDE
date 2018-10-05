package de.upb.sede.webinterfaces.server;

import de.upb.sede.util.FileUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		CommandTree rm = new CommandTree(
				node(new Strings("rm"),
						node(new File(".", false, true)
								.addExe(t ->
										{
											String filePath =
													CommandTree.lastMatch(t) + "/"
															+ Arrays.stream(CommandTree.rest(t)).collect(Collectors.joining("/"));
											try (FileWriter fwOb = new FileWriter(filePath, false);){
												PrintWriter pwOb = new PrintWriter(fwOb, false);
												pwOb.flush();
												pwOb.close();
											} catch (IOException e) {
												e.printStackTrace();
											}
											return "Log file cleared."
										}

								)))
		);
		scl.addCommandHandle(cat);
		scl.addCommandHandle(rm);
	}
}
