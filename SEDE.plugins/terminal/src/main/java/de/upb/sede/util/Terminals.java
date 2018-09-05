package de.upb.sede.util;

import de.upb.sede.test.RemoteConsole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.builtins.telnet.Telnet;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;

public class Terminals {
	private final static Logger logger = LogManager.getLogger();
	public static void setupLogging() {
		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
	}

	public static void startShell(Telnet.ShellProvider shellProvider, Terminal terminal, boolean asynchron) {
		Thread t = new Thread(() -> shellProvider.shell(terminal, new HashMap<>()));
		t.start();
		if(!asynchron) {
			try {
				t.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void startTellnetServer(Telnet.ShellProvider shellProvider, int port, boolean asynchron) {
		Thread t = new Thread(() -> {
			try {
				Terminal terminal = TerminalBuilder.builder().system(false).streams(Streams.EmptyInStream(), Streams.DiscardOutStream()).build();
				Telnet telnet = new Telnet(terminal, shellProvider);
				telnet.telnetd(new String[]{"telnetd", "-p", String.valueOf(port), "start"});
			} catch (IOException e) {
				logger.error("Couldnt start telnetd server: ", e);
			}
		});
		t.start();
		if(!asynchron) {

		}
	}
}
