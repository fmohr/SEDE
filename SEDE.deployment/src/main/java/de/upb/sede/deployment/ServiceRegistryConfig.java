package de.upb.sede.deployment;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ServiceRegistryConfig {
	private final String DEFAULT_CONFIG_PATH = ".";
	private Optional<JSONObject> parsedPathConfigFile;
	Path pathToConfigFile;

	ServiceRegistryConfig(String[] args) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("paths_config", "p", true,
				"Configuration file that declares where the service assemblies are stored.");
		options.addOption("help", "h", false, "Displays the available commands.");
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.err.println("Commands to configure " + this.getClass().getSimpleName() + "could not be obtained.");
			throw e;
		}
		if (cmd.hasOption("h")) {
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.setWidth(300);
			helpFormatter.printHelp("Service Deployment", options);
			System.exit(0);
		}
		String path = (cmd.hasOption("p")) ? cmd.getOptionValue("p") : DEFAULT_CONFIG_PATH;
		pathToConfigFile = Paths.get(path);
		if (!Files.isRegularFile(pathToConfigFile)) {
			throw new ParseException(pathToConfigFile + " is no file.");
		}
		File configFile = new File(path);
		if (!configFile.exists()) {
			throw new ParseException(pathToConfigFile + " does not exist.");
		}
		parsedPathConfigFile = parseConfigFile(path);
	}

	private Optional<JSONObject> parseConfigFile(String path) {
		JSONParser parser = new JSONParser();
		Optional<JSONObject> jsonConfig = Optional.empty();
		try {
			Object configObj = parser.parse(path);
			jsonConfig = Optional.of((JSONObject) configObj);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to parse path configuration file.");
		}
		return jsonConfig;
	}
	
	public Optional<JSONObject> getParsedPathConfigFile() {
		return parsedPathConfigFile;
	}

}
