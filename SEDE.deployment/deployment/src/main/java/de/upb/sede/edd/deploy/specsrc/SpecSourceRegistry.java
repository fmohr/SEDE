package de.upb.sede.edd.deploy.specsrc;

import de.upb.sede.edd.EDDHome;
import de.upb.sede.edd.deploy.SpecURI;
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.TTLCache;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SpecSourceRegistry {

    private List<SpecSource> sources = new ArrayList<>();

    private  EDDHome home;

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


    public SpecSourceRegistry(EDDHome home) {
        this.home = home;
    }

    public Optional<SpecSource> find(SpecURI specURI) {
        return find(specURI.getAddress());
    }

    public Optional<SpecSource> find(String sourceName) {
        return sources
            .stream()
            .filter(specSource -> specSource.getName().equalsIgnoreCase(sourceName))
            .findFirst();
    }

    public SpecSource createSpecSource(SpecURI specURI) {
        SpecSource specSource;
        switch(specURI.getScheme()) {
            case "cp":
            case "classpath":
                specSource = new ClasspathSpecSource(specURI);
                break;
            case "http" :
                specSource = new MarketSpecSource(specURI);
                break;
            case "file" :
                specSource = new FileSpecSource(specURI);
                break;
            default:
                throw new IllegalArgumentException("Unkown uri scheme: " + specURI.getScheme() + " of given uri: " + specURI.getURI());
        }
        sources.add(specSource);
        return specSource;
    }

    public SpecSource findOrCreate(SpecURI specURI) {
        return find (
            specURI
                )
                .orElse(
                    createSpecSource(specURI)
                );
    }
}
