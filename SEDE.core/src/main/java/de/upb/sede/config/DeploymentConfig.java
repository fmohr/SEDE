package de.upb.sede.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.spec.DeploymentSpecification;
import de.upb.sede.util.DynTypeField;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.Maps;
import de.upb.sede.util.Uncheck;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DeploymentConfig {

    private List<DynTypeField> deploymentSpecs = new ArrayList<>();

    private static Logger logger = LoggerFactory.getLogger(DeploymentConfig.class);

    /**
     * Reads the configuration files from configPaths and appends them into itself..
     */
    public DeploymentConfig(String... configPaths) {
        this();
        appendConfigFromFiles(configPaths);
    }

    public DeploymentConfig() {
        super();
    }

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
        List<DeploymentSpecification> loadedConfigs = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JavaType specListType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, DeploymentSpecification.class);
        for (String jsonString : jsonStrings) {
            DynTypeField deploymentSpecs = DynTypeField.fromJson(jsonString);
            ((List<DeploymentSpecification>) deploymentSpecs.cast(specListType))
                .forEach(DeploymentSpecification::validate);
            this.deploymentSpecs.addAll(deploymentSpecs.cast(List.class));
        }
    }


    public boolean hasCollection(String serviceCollectionName) {
        return this.deploymentSpecs.stream().anyMatch(dynType ->
            dynType.cast(DeploymentSpecification.class).test(serviceCollectionName));
    }

    public String serialize() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(deploymentSpecs);
        } catch (JsonProcessingException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
    }
}
