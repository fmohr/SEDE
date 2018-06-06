package de.upb.sede.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OnthologicalTypeConfig extends Configuration {
	private static Logger logger = LogManager.getLogger(OnthologicalTypeConfig.class.getSimpleName());
	private static final String SEMANTIC_TYPE = "semantic_type";
	private static final String ONTHOLOGICAL_CASTER = "onthological_caster";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2009780076326140239L;

	/**
	 * Reads the configuration files from configPaths and appends them into itself..
	 */
	public OnthologicalTypeConfig(String... configPaths) {
		this();
		appendConfigFromFiles(configPaths);
	}

	public OnthologicalTypeConfig() {
		super();
	}

	public boolean hasOnthologicalType(String fullClassName) {
		return this.containsKey(fullClassName);
	}

	public String getOnthologicalType(String fullClassName) {
		if (hasOnthologicalType(fullClassName)) {
			Map<String, Object> configEntry = (Map<String, Object>) this.get(fullClassName);
			String onthologicalType = (String) configEntry.get(SEMANTIC_TYPE);
			if (onthologicalType != null)
				return onthologicalType;
			else {
				throw new RuntimeException(fullClassName + " doesn't define the " + SEMANTIC_TYPE + " field.");
			}
		} else {
			logger.error("No onthological type found for: " + fullClassName);
			throw new RuntimeException("No onthological type found for: " + fullClassName);
		}
	}

	public String getOnthologicalCaster(String fullClassName) {
		if (hasOnthologicalType(fullClassName)) {
			Map<String, Object> configEntry = (Map<String, Object>) this.get(fullClassName);
			String onthologicalType = (String) configEntry.get(ONTHOLOGICAL_CASTER);
			if (onthologicalType != null)
				return onthologicalType;
			else {
				throw new RuntimeException(fullClassName + " doesn't define the " + ONTHOLOGICAL_CASTER + " field.");
			}
		} else {
			logger.error("No onthological type found for: " + fullClassName);
			throw new RuntimeException("No onthological type found for: " + fullClassName);
		}
	}

	public List<String> getClassesForSemanticType(String semanticType) {
		List<String> result = new ArrayList<>();
		for (Entry<String, Object> onthologyRule : this.entrySet()) {
			String className = onthologyRule.getKey();
			Map<String, Object> onthologyAttributes = (Map<String, Object>) onthologyRule.getValue();
			String semanticTypeInRule = (String) onthologyAttributes.get(SEMANTIC_TYPE);
			if (semanticType.equals(semanticTypeInRule)) {
				result.add(className);
			}
		}
		return result;
	}

	public Set<String> knownTypes() {
		return this.keySet();
	}

}
