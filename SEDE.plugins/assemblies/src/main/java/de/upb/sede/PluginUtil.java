package de.upb.sede;

import de.upb.sede.util.Terminals;
import de.upb.sede.util.WebUtil;
import de.upb.sede.util.server.TerminalCommandListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;


public class PluginUtil {

	private final static Logger logger = LogManager.getLogger();

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
