package de.upb.sede.edd;

import de.upb.sede.edd.deploy.*;
import de.upb.sede.edd.deploy.group.GroupRepository;
import de.upb.sede.edd.deploy.model.DeploymentSpecification;
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.edd.process.ClassPath;
import de.upb.sede.edd.process.DefaultProcessHandle;
import de.upb.sede.edd.process.JavaProcessHandleBuilder;
import de.upb.sede.util.*;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class EDD {

    private static final EDD instance = new EDD();

    public static EDD getInstance() {
        return instance;
    }

    private EDDHome home = new EDDHome();

    private TTLCache<DeploymentSpecificationRegistry> registryTTLCache = new TTLCache<>(20, TimeUnit.SECONDS, () -> {
       File homeRegistryFile = home.getRegistry();
       String registryJsonString;
       if(homeRegistryFile.exists()) {
           registryJsonString = FileUtil.readFileAsString(homeRegistryFile.getPath());
       } else {
           registryJsonString = FileUtil.readResourceAsString("deployment/sede.services-deployconf.json");
       }
        return DeploymentSpecificationRegistry
            .fromString(registryJsonString);
    });

    private GroupRepository groupRepository = new GroupRepository(home, registryTTLCache);

    public TTLCache<DeploymentSpecificationRegistry> getRegistryCache() {
        return registryTTLCache;
    }

    public GroupRepository getGroupRepository() {
        return groupRepository;
    }
}
