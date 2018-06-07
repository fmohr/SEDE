package de.upb.sede.deployment;

import de.upb.sede.util.FileUtil;
import de.upb.sede.webinterfaces.client.HTTPClientRequest;

import java.util.Arrays;

/**
 * This class offers a main method to be used to upload configurations onto a
 * gateway.
 */
public class ConfigUploader {
	public static void main(String[] args) {
		if (args == null || args.length < 2) {
			System.err.println("Privide the host of the gateway as the first argument "
					+ "and the path to the configurations as the rest of the arguments."
					+ "\nFor example:\n\tlocalhost:6000 config/a-classconf.json config/b-typeconf.json");
		}
		System.out.println("Config uploader inputs: " + Arrays.toString(args));
		String gatewayHost = args[0];

		String url = gatewayHost + "/add-conf/";
		for (int i = 1; i < args.length; i++) {
			String configPath = args[i];

			/*
			 * Decide if its typeconfig or classconfig.
			 */
			boolean classConf;
			if (configPath.matches("(.*?)classconf\\.json$")) {
				classConf = true;
			} else if (configPath.matches("(.*?)typeconf\\.json$")) {
				classConf = false;
			} else {
				System.err.println("Configuration " + configPath + " doesn't end with classconf.json or"
						+ " nor with typeconf.json. Ignoring it.");
				continue;
			}

			/*
			 * Read the file into string.
			 */
			String configJson;
			try {
				configJson = FileUtil.readFileAsString(configPath);
			} catch (Exception ex) {
				System.err.println("Problem reading config " + configPath + ": " + ex.getMessage());
				continue;
			}

			/*
			 * Send the configuration to gateway server:
			 */
			HTTPClientRequest request = new HTTPClientRequest(url + (classConf ? "classes/" : "types/"));
			try {
				String answer = request.send(configJson);
				if (!answer.isEmpty()) {
					System.err.println("Error from server sending " + configPath + ": " + answer);
					continue;
				}
			} catch (Exception ex) {
				System.err.println("Problem connecting to gateway " + url + ": " + ex.getMessage());
				continue;
			}
			System.out.println("Uploaded successfully: " + configPath);
		}
	}
}
