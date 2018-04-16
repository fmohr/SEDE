package de.upb.sede.config;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.upb.sede.util.FileUtil;

public class OnthologicalTypeConfig {

	
	private Map<String, JsonNode> rawConfiguration = new HashMap<>();
	
	private Map<Object,Object> holeConfig = new HashMap<>();
	

	/**
	 * Reads the cofiguration files from configPaths and appends them into itself..
	 */
	public OnthologicalTypeConfig(String... configPaths) throws IOException {
		this();
		appendConfigFromFiles(configPaths);
	}
	
	public OnthologicalTypeConfig() {
		super();
	}

	/**
	 * Appends class configurations from files to the existing ones.
	 */
	public void appendConfigFromFiles(String... configFiles)  {
		if(configFiles.length == 0) {
			return;
		}
		List<String> jsonStringList = new ArrayList<>(configFiles.length);
		for(String filePath : configFiles) {
			String jsonString = FileUtil.readFileAsString(filePath);
			jsonStringList.add(jsonString);
		}
		String[]  jsonStringArr = jsonStringList.toArray(new String[jsonStringList.size()]);
		this.appendConfigFromJsonStrings(jsonStringArr);
	}

	/**
	 * Appends class configurations from json-strings to the existing ones.
	 */
	public void appendConfigFromJsonStrings(String... jsonStrings) {
		if(jsonStrings.length == 0) {
			return;
		}
		TypeReference<HashMap<String,JsonNode>> typeRef = new TypeReference<HashMap<String,JsonNode>>() {};
        ObjectMapper mapper = new ObjectMapper(); 
        for(String jsonString : jsonStrings) {
        		// iterate over configs and append them to the existing configuration
            HashMap<String, JsonNode> loadedConfig;
			try {
				loadedConfig = mapper.readValue(jsonString, typeRef);
	            rawConfiguration.putAll(loadedConfig);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
        }
        // reset this configuration, in order to call resolveInheritances on a un extended configurations
		this.clear(); // clear all configurations.
        this.putAll(rawConfiguration);
        this.resolveInheritances();
		
	}
	
}
