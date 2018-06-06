package de.upb.sede.config;

import java.util.*;

import de.upb.sede.util.Maps;
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
		List<HashMap<String, Object>> loadedConfigs = new ArrayList<>();
		for (String jsonString : jsonStrings) {
			// iterate over configs and append them to the existing configuration
			try {
				loadedConfigs.add((HashMap<String, Object>) parser.parse(jsonString));
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}

		}
		HashMap<String, Object>[] maps = loadedConfigs.toArray(new HashMap[0]);
		appendConfigFromJsonMaps(maps);

	}

	/**
	 * Appends class configurations from json-Maps to the existing ones.
	 */
	public void appendConfigFromJsonMaps(Map<String, Object>... jsonMaps) {
		for(Map<String, Object> jsonMap : jsonMaps) {
			Set intersection = Maps.keyIntersection(jsonMap, rawConfiguration);
			if(!intersection.isEmpty()) {
				throw new RuntimeException("Cannot redefine Classes:\n" + intersection.toString());
			}
			rawConfiguration.putAll(jsonMap);
		}
		// reset this configuration, in order to call resolveInheritances on a
		// unextended configurations
		this.clear(); // clear all configurations.
		this.putAll(rawConfiguration);

	}
}
