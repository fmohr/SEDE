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

public class OnthologicalTypeConfig extends Configuration{
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

	
}
