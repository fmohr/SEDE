package de.upb.sede.gateway.configuration;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.gateway.Gateway;
import de.upb.sede.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class GatewayConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(GatewayConfiguration.class);

    @Bean
    public Gateway gateway() {
        List<String> configsToBeLoaded = Arrays.asList("config/builtin-classconf.json",
                "config/builtin-typeconf.json",
                "config/c2imaging-classconf.json",
                "config/c2imaging-typeconf.json",
                "config/imaging-classconf.json",
                "config/imaging-typeconf.json",
                "config/sl-ml-classifiers-classconf.json",
                "config/sl-ml-typeconf.json",
                "config/weka-ml-classifiers-classconf.json",
                "config/weka-ml-clusterers-classconf.json",
                "config/weka-ml-pp-classconf.json",
                "config/weka-ml-typeconf.json",
                "config/demo-classconf.json",
                "config/demo-typeconf.json");

        ClassesConfig classesConfig = new ClassesConfig();
        OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
        loadDefaultConfigs(classesConfig, typeConfig, configsToBeLoaded);

        Gateway gateway = new Gateway(classesConfig, typeConfig);


        try {
            logger.info("Reading deployconf from resources: \"sede.services-deployconf.json\"");
            String deployConf = FileUtil.readResourceAsString("config/sede.services-deployconf.json");
            gateway.getDeploymentConfig().appendConfigFromJsonStrings(deployConf);
        } catch(Exception ex) {
            logger.warn("Couldnt find deployconf in resources..", ex);
        }
        return gateway;
    }

    static void loadDefaultConfigs(ClassesConfig classesConfig, OnthologicalTypeConfig typeConfig,
                                   List<String> configsToBeLoaded) {
        for (String configuration : configsToBeLoaded) {
            loadDefaultConfiguration(classesConfig, typeConfig, configuration);
        }
    }


    static void loadDefaultConfiguration(ClassesConfig classesConfig, OnthologicalTypeConfig typeConfig,
                                         String configuration) {
        File configFile = new File(configuration).getAbsoluteFile();

        /*
         * look for json files ending with classconf.json or typeconf.json in the given
         * list and try to append them to the class/type configuration:
         */
        if (configFile.isDirectory()) {
            /*
             * load every class conf file from the given folder:
             */
            logger.info(
                    "Config file '{}' is a directory, loading every classconf and typeconf files in it...",
                    configuration);
            List<String> additionalConfigs = FileUtil.listAllFilesInDir(configFile.getAbsolutePath(), "(.*?)\\.json$").stream()
                    .map(s -> configFile + "/" + s) // prepend the folder:
                    .collect(Collectors.toList());
            loadDefaultConfigs(classesConfig, typeConfig, additionalConfigs);
        } else{

            String configStringContent;
            if(configFile.exists()) {
                configStringContent = FileUtil.readFileAsString(configuration);
            } else {
                try{
                    logger.info("Reading config file {} from classpath..", configuration);
                    configStringContent = FileUtil.readResourceAsString(configuration);
                } catch (Exception ex) {
                    logger.error(
                            "Config file '{}' could not be parsed. (Skipping it).",
                            configuration, ex);
                    return;
                }
            }
            if (configuration.matches("(.*?)classconf\\.json$")) {
                try {
                    classesConfig.appendConfigFromJsonStrings(configStringContent);
                    logger.info("Added classes config from: {}", configuration);
                } catch (Exception ex) {
                    logger.error("Problem loading class configuration from {}:\n", configuration, ex);
                }
            } else if (configuration.matches("(.*?)typeconf\\.json$")) {
                try {
                    typeConfig.appendConfigFromJsonStrings(configStringContent);
                    logger.info("Added type config from: {}", configuration);
                } catch (Exception ex) {
                    logger.error("Problem loading type configuration from {}:\n", configuration, ex);
                }
            }
            else{
                logger.error("Cannot differentiate configuration {}, as it doesn't end with classconf nor typeconf.", configuration);
            }
        }
    }
}
