package de.upb.sede;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.util.Terminals;
import de.upb.sede.util.WebUtil;
import de.upb.sede.util.server.TerminalCommandListener;


public class PluginUtil {

	private final static Logger logger = LoggerFactory.getLogger(PluginUtil.class);

	public static void enablePlugin_SystemTerminal(TerminalCommandListener terminalListener){

		try{
			Terminal consoleTerminal = TerminalBuilder.terminal();
			Terminals.startShell(terminalListener.shellProvider(), consoleTerminal, true);
		} catch(Exception ex) {
			logger.error("Couldn't connect to system terminal: " + ex.getMessage());
			logger.trace("Terminal error: ", ex);
		}
	}

	public static void enablePlugin_TellnetServer(TerminalCommandListener terminalListener) {
		try{
			int tellnetPort = WebUtil.nextFreePort(2200);
			Terminals.startTellnetServer(terminalListener.shellProvider(), tellnetPort, true);
		} catch(Exception ex) {
			logger.error("Couldn't start tellnet server: " + ex.getMessage());
			logger.trace("Tellnet boot-up error: ", ex);
		}
	}
}
