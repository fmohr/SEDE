package de.upb.sede.test;

import de.upb.sede.util.FileUtil;
import de.upb.sede.util.Terminals;
import de.upb.sede.util.WebUtil;
import de.upb.sede.util.server.TerminalCommandListener;
import de.upb.sede.webinterfaces.server.CommandTree;
import org.jline.builtins.telnet.Telnet;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import static de.upb.sede.webinterfaces.server.CommandTree.*;
import static de.upb.sede.webinterfaces.server.Command.*;

public class JLineTests {



	private void testJL1() throws IOException {
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		String read1 = reader.readLine();
//		System.out.println(read1);

		Terminal terminal = TerminalBuilder.terminal();
		LineReader lineReader = LineReaderBuilder.builder()
				.terminal(terminal)
				.build();
		String read = lineReader.readLine("Enter Command:");
		PrintWriter writer = terminal.writer();
		writer.println(new AttributedStringBuilder()
				.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE))
				.append("You Typed: " + read)
				.toAnsi()
		);
		writer.flush();

	}

	private void TerminalCommandListenerTest() throws IOException {
		CommandTree ct1 = new CommandTree(node(new Strings("say", "repeat"), node(rest().addExe(t ->
				"ok: '" + lastMatch(t).replaceAll("\\$", " ") + "'"
		))));
		CommandTree ct2 = new CommandTree(node(new Strings("ask"), node(new Strings("how-i-am").addExe(t ->
				"How are you?"
		)), node(new Strings("about-the-weather").addExe(t->
				"How is the weather?"))));
		CommandTree ct3 = new CommandTree(node(new Strings("read"), node(new File(".", false, true).addExe(t ->
				{
					String path = lastMatch(t);
					String fileContent = FileUtil.readFileAsString(path);
					return fileContent;
				}
		))));
		TerminalCommandListener tcl = new TerminalCommandListener();
		tcl.listenToCommands(ct1);
		tcl.listenToCommands(ct2);
		tcl.listenToCommands(ct3);
		Terminal terminal = terminal = TerminalBuilder.terminal();
		Terminals.startShell(tcl.shellProvider(), terminal, true);
		int port = WebUtil.nextFreePort(2200);
		Terminals.startTellnetServer(tcl.shellProvider(), port,true);
	}

	public static void main(String args[]) throws IOException {
		Terminals.setupLogging();
		JLineTests tests =  new JLineTests();
		tests.TerminalCommandListenerTest();
	}
}
