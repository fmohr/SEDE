package de.upb.sede.util.server;

import static de.upb.sede.webinterfaces.server.CommandTree.node;

import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jline.builtins.Completers;
import org.jline.builtins.telnet.Telnet;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.server.Command;
import de.upb.sede.webinterfaces.server.Command.ConsumeNothing;
import de.upb.sede.webinterfaces.server.Command.ConsumeRest;
import de.upb.sede.webinterfaces.server.Command.Strings;
import de.upb.sede.webinterfaces.server.CommandFormatMismatch;
import de.upb.sede.webinterfaces.server.CommandListener;
import de.upb.sede.webinterfaces.server.CommandTree;
import de.upb.sede.webinterfaces.server.CommandTree.Node;

public class TerminalCommandListener implements CommandListener {
	private final static Logger logger = LoggerFactory.getLogger(TerminalCommandListener.class);

	private final List<CommandTree> commandTrees = new ArrayList<>();


	public TerminalCommandListener() {
		List<Completer> completerList = new ArrayList<>();
		completerList.add(new StringsCompleter("exit"));

	}

	public Telnet.ShellProvider shellProvider() {
		return new CommandShell(new ArrayList<>(commandTrees));
	}

	private static class CommandShell implements  Telnet.ShellProvider {
		List<CommandTree> shellCommands;

		private CommandShell(List<CommandTree> shellCommands) {
			this.shellCommands = shellCommands;
		}

		public void shell(Terminal terminal, Map<String, String> environment) {
			ShellSession session = new ShellSession();
			CommandTree exitSessionCommand = new CommandTree(node(new Strings("exit"), node(new Strings("session").addExe(
					t -> {
						session.sessionIsRunning = false;
						return "Closing session...";
					}
			))));
			List<CommandTree> shellSessionCommands = new ArrayList<>(this.shellCommands);
			shellSessionCommands.add(exitSessionCommand);

			CommandTree mergedCommandTree = new CommandTree(node(Command.nothing()));
			shellSessionCommands.stream().forEachOrdered(mergedCommandTree::addOptionToRoot);
			Completer sessionCommandCompleter = transformToCompleter(mergedCommandTree);

			session.completer = sessionCommandCompleter;
			session.commandTree = mergedCommandTree;

			session.run(terminal, environment);
		}
	}
	private static class ShellSession  {

		private boolean sessionIsRunning = true;
		private Completer completer = new NullCompleter();
		private CommandTree commandTree = new CommandTree(node(Command.rest()));

		public void run(Terminal terminal, Map<String, String> environment) {
			while(sessionIsRunning){
				LineReader lineReader = LineReaderBuilder.builder()
						.terminal(terminal)
						.completer(completer)
						.build();

				String read = lineReader.readLine("Enter command: ");
				String[] splits = read.split(" ");
				String answer;
				try {

					answer = commandTree.execute(splits);
				} catch(CommandFormatMismatch wrongCommand) {
					answer = "Error: Unkown command:\n " + read;
				} catch(Exception ex) {
					answer = "Error during execution of command '" + read + "': " + ex.getMessage()+ "\nStacktrace\n" +
							Streams.ErrToString(ex);

				}
				PrintWriter writer = terminal.writer();
				writer.println(new AttributedStringBuilder()
						.append(answer));
				writer.flush();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error("Interrupted while closing session: ", e);
			}
		}
	}

	@Override
	public void listenToCommands(CommandTree responder) {
		if(!commandTrees.contains(responder)) {
			this.commandTrees.add(responder);
		} else {
			logger.error("Commandtree was added twice.");
		}

	}

	public static Completer transformToCompleter(CommandTree ct) {
		if(ct.getRoot().getCommand() instanceof ConsumeNothing) {
			List<Completer> children = new ArrayList<>();
			for(Node childNode : ct.getRoot().getChildren()){
				CommandTree childTree = new CommandTree(childNode);
				children.add(transformToCompleter(childTree));
			}
			AggregateCompleter completer = new AggregateCompleter(children);
			return completer;
		} else {
			Completers.TreeCompleter.Node root = transformNode(ct.getRoot());
			Completers.TreeCompleter treeCompleter = new Completers.TreeCompleter(root);
			return treeCompleter;
		}
	}

	public static Completers.TreeCompleter.Node transformNode(CommandTree.Node commandNode) {
		if(commandNode.getCommand() instanceof ConsumeRest) {
			return Completers.TreeCompleter.node(new NullCompleter());
		}
		List<Completers.TreeCompleter.Node> childNodes = new ArrayList<>();
		Completer commandCompleter = transformCommand(commandNode.getCommand());
		commandNode.getChildren().stream().map(TerminalCommandListener::transformNode).forEachOrdered(childNodes::add);

		return new Completers.TreeCompleter.Node(commandCompleter, childNodes);

	}

	public static Completer transformCommand(Command command) {
		if(command instanceof Strings) {
			Strings strings = (Strings) command;
			return new StringsCompleter(strings.getMatches().toArray(new String[0]));
		} else if(command instanceof Command.File){
			Command.File fileCommand = (Command.File) command;
			if(!fileCommand.isDirectories()) {
			} if (!fileCommand.isFiles()) {
				return new Completers.DirectoriesCompleter(Paths.get(fileCommand.getPath()));
			} else {
				return new Completers.FilesCompleter(Paths.get(fileCommand.getPath()));
			}
		} else if(command instanceof ConsumeRest || command instanceof ConsumeNothing) {
			return new NullCompleter();
		}
		else {
			throw new RuntimeException("Cannot transform to completer: " + command.getClass().getSimpleName());
		}

	}

}
