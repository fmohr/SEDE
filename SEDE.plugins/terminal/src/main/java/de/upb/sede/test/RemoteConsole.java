package de.upb.sede.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.jline.builtins.Completers;
import org.jline.builtins.telnet.Telnet;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.util.FileUtil;
import de.upb.sede.util.Streams;

public class RemoteConsole {
	private final static Logger logger = LoggerFactory.getLogger(RemoteConsole.class);
	public static void main(String[]  args) throws IOException, InterruptedException {
		Terminal t = TerminalBuilder.builder().system(false).streams(Streams.EmptyInStream(), Streams.DiscardOutStream()).build();
		Attributes attrs = t.getAttributes();
		Telnet telnet = new Telnet(t, new SP());
		telnet.telnetd(new String[]{"telnetd", "-i", "localhost", "-p", "22000", "start"});
		Thread.sleep(1000000);
	}

	static class SP implements  Telnet.ShellProvider {

		@Override
		public void shell(Terminal terminal, Map<String, String> environment) {
			while(true){
				Completer completer1 = new ArgumentCompleter(
						new StringsCompleter("read"),
						new Completers.FilesCompleter(new File(".")),
						NullCompleter.INSTANCE);
				Completer completer2 = new ArgumentCompleter(
						new StringsCompleter("write"),
						NullCompleter.INSTANCE);

				AggregateCompleter completer = new AggregateCompleter(completer1, completer2);

				LineReader lineReader = LineReaderBuilder.builder()
						.terminal(terminal)
						.completer(completer)
						.build();

				String read = lineReader.readLine("Enter Command:");
				String[] splitRead = read.split(" ");
				if(read.startsWith("read ") && splitRead.length >= 2) {
					String filepath =splitRead[1];
					String fileContent = FileUtil.readFileAsString(filepath);
					PrintWriter writer = terminal.writer();
					writer.println(new AttributedStringBuilder()
							.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE))
							.append("File " + filepath + ":\n"
							+ fileContent)
					);
					writer.flush();
				} else if(read.startsWith("write ") && splitRead.length >= 3) {
					String filepath =splitRead[1];
					String fileContent = splitRead[2];
					logger.debug("Writing {} to {}.", fileContent, filepath);
					FileUtil.writeStringToFile(filepath, fileContent);

					PrintWriter writer = terminal.writer();
					writer.println(new AttributedStringBuilder()
							.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).bold())
							.append("File " + filepath + " wrote."
									)
					);
					writer.flush();

				}
			}

		}
	}
}
