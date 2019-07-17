package de.upb.sede.util;

import de.upb.sede.util.Cache;

import java.io.File;
import java.util.Objects;

public class SystemSettingLookup implements Cache<String> {

    private final String defaultValue, systemPropertyKey, envVarKey;


    public SystemSettingLookup(String defaultValue, String systemPropertyName, String envVarName) {
        this.defaultValue = Objects.requireNonNull(defaultValue);
        this.systemPropertyKey = systemPropertyName;
        this.envVarKey = envVarName;
    }

    public String lookup() {
        String setting = null;
        if (systemPropertyKey != null && (setting = System.getProperty(systemPropertyKey)) != null) {
            return setting;
        }
        if (envVarKey != null && (setting = System.getenv(envVarKey)) != null) {
            return setting;
        }
        return defaultValue;
    }

    public File lookupFile() {
        return new File(lookup());
    }

    @Override
    public String access() {
        return lookup();
    }
}
