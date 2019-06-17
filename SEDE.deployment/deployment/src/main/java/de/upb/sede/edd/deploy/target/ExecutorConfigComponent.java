package de.upb.sede.edd.deploy.target;

import de.upb.sede.util.ExecutorConfigurationCreator;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.OptionalField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public class ExecutorConfigComponent extends TargetComponent {

    private final static Logger logger = LoggerFactory.getLogger(ExecutorConfigComponent.class);

    private File executorDir;

    private File executorConfigFile;

    private String executorId;

    private Set<String> services = new LinkedHashSet<>();

    private OptionalField<String> gateway;

    private File serviceDir;

    public ExecutorConfigComponent(String displayName, File executorDir, String executorID, File serviceDir) {
        this.executorDir = executorDir;
        this.executorId = executorID;
        executorConfigFile = new File(executorDir,
            "executor-config-" + executorID  + ".json");
        super.setDisplayName(displayName);
        gateway = OptionalField.empty();
        this.serviceDir = serviceDir;
    }

    public File getExecutorDir() {
        return executorDir;
    }

    public Set<String> getServices() {
        return services;
    }

    public Optional<String> getGateway() {
        return gateway.opt();
    }

    public void setGateway(String gateway) {
        this.gateway = OptionalField.ofNullable(gateway);
    }

    public String getExecutorId() {
        return executorId;
    }

    public File getExecutorConfigFile() {
        logger.info("{}: creating config file for executor {}.", getDisplayName(), getExecutorId());
        ExecutorConfigurationCreator creator = new ExecutorConfigurationCreator();
        if(gateway.isPresent()) {
            creator.withGateway(gateway.get());
        }
        logger.debug("{}: executor {} supports the following services: {}", getDisplayName(), getExecutorId(), services);
        creator.withSupportedServices(services);
        creator.withExecutorId(executorId);
        creator.withServiceStoreLocation(serviceDir.getAbsolutePath());
        FileUtil.writeStringToFile(executorConfigFile.getAbsolutePath(), creator.toString());
        return executorConfigFile;
    }
}
