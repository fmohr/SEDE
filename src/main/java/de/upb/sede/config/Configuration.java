package de.upb.sede.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.upb.sede.util.FileUtil;

public class Configuration extends HashMap<String, Object> {
	private static final long serialVersionUID = 22870752590407834L;
	private Map<String, Object> rawConfiguration = new HashMap<>();

	/**
	 * Appends class configurations from files to the existing ones.
	 */
	public void appendConfigFromFiles(String... configFiles) {
		if (configFiles.length == 0) {
			return;
		}
		List<String> jsonStringList = new ArrayList<>(configFiles.length);
		for (String filePath : configFiles) {
			String jsonString = FileUtil.readFileAsString(filePath);
			jsonStringList.add(jsonString);
		}
		String[] jsonStringArr = jsonStringList.toArray(new String[jsonStringList.size()]);
		this.appendConfigFromJsonStrings(jsonStringArr);
	}

	/**
	 * Appends class configurations from json-strings to the existing ones.
	 */
	public void appendConfigFromJsonStrings(String... jsonStrings) {
		if (jsonStrings.length == 0) {
			return;
		}
		JSONParser parser = new JSONParser();
		for (String jsonString : jsonStrings) {
			// iterate over configs and append them to the existing configuration
			HashMap<String, Object> loadedConfig;
			try {
				loadedConfig = (HashMap<String, Object>) parser.parse(jsonString);
				rawConfiguration.putAll(loadedConfig);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		// reset this configuration, in order to call resolveInheritances on a
		// unextended configurations
		this.clear(); // clear all configurations.
		this.putAll(rawConfiguration);
	}
}
