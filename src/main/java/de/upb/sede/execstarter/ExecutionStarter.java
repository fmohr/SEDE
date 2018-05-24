package de.upb.sede.execstarter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ExecutionStarter {
	public static void main(String [] args) {	
		try {
			ExecutionStartConfig startConfiguration = new ExecutionStartConfig(args);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
