package de.upb.sede.execstarter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore.Entry;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ExecutionStartConfig {
	Path pathToConfigFile;

	ExecutionStartConfig(String[] args) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("deployment_config", "f", true,
				"Configuration file that declares the files that the executor should be initilized with");
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.err.println("Commands to configure " + this.getClass().getSimpleName() + "could not be obtained.");
			throw e;
		}
		String path = cmd.getOptionValue("f");
		pathToConfigFile = Paths.get(path);
		if (!Files.isRegularFile(pathToConfigFile)) {
			throw new ParseException(pathToConfigFile + " is no file.");
		}
		File configFile = new File(path);
		if (!configFile.exists()) {
			throw new ParseException(pathToConfigFile + " does not exist.");
		}
		parseConfigFile(path);
	}

	private void parseConfigFile(String path) {
		JSONParser parser = new JSONParser();
		try {
			Object configObj = parser.parse(path);
			JSONObject jsonConfig = (JSONObject) configObj;
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
