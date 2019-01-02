package de.upb.sede.webinterfaces.server;

import de.upb.sede.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

import static de.upb.sede.webinterfaces.server.CommandTree.*;
import static de.upb.sede.webinterfaces.server.Command.*;

public class StdShellCommands {
	private final static Logger logger = LoggerFactory.getLogger(StdShellCommands.class);
	public static void enablePlugin(ServerCommandListeners scl) {
		CommandTree cat = new CommandTree(
				node(new Strings("cat"),
						node(new Command.File(".", false, true)
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
						node(new Command.File(".", false, true)
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
											return "Log file cleared.";
										}

								)))
		);
		CommandTree ls = new CommandTree(
				node(new Strings("la"),
						node(new Command.File(".", true, false)
								.addExe(t ->
										{
											String filePath =
													CommandTree.lastMatch(t) + "/"
															+ Arrays.stream(CommandTree.rest(t)).collect(Collectors.joining("/"));
											String command  = "cd " + filePath + " && ls -a";
											StringBuilder processOut = null;
											try {
												Process r = Runtime.getRuntime().exec(command,null);
												BufferedReader br=new BufferedReader(new InputStreamReader(r.getInputStream()));
												String line = br.readLine();
												processOut = new StringBuilder();
												while(line != null) {
													processOut.append(line + "\n");
													line = br.readLine();
												}
												br.close();
											} catch (IOException e) {
												logger.error("During " + command + ": ", e);
												if(processOut != null){
													return processOut.toString();
												}
											}
											return FileUtil.listAllFilesInDir(filePath, ".*").stream().collect(Collectors.joining("\n"));
										}

								)))
		);

		scl.addCommandHandle(cat);
		scl.addCommandHandle(rm);
		scl.addCommandHandle(ls);
	}
}
