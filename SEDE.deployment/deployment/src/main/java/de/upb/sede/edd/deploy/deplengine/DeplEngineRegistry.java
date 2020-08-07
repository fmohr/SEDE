package de.upb.sede.edd.deploy.deplengine;

import de.upb.sede.edd.EDD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeplEngineRegistry {

    private static final Logger logger = LoggerFactory.getLogger(DeplEngineRegistry.class);

    private List<DeplEngine> engines = new ArrayList<>();

    private EDD edd;

    public static final String LOCAL_ENGINE_NAME = "local";

    public DeplEngineRegistry(EDD edd) {
        this.edd = edd;
    }

    public DeplEngine getDefault() {
        if(getDeplEngine(null).isPresent()) {
            return getDeplEngine(null).get();
        }
        else {
            DeplEngine engine = new LocalDeplEngine(edd);
            engines.add(engine);
            return getDefault();
        }
    }

    public Optional<DeplEngine> getDeplEngine(String name) {
        name = tranform(name);
        logger.trace("Engine '{}' requested", name);
        for(DeplEngine engine : engines) {
            if(engine.getName().equalsIgnoreCase(name)) {
                logger.trace("Engine '{}' was found.", name);
                return Optional.of(engine);
            }
        }
        logger.trace("Engine '{}' was not found.", name);
        return Optional.empty();
    }

    private String tranform(String name) {
        if(name == null) {
            return LOCAL_ENGINE_NAME;
        }

        return name.toLowerCase();
    }

    public List<DeplEngine> getEngines() {
        return engines;
    }

    public DeplEngine createRemoteEngine(String name, String remoteHostAddress) {
        if(getDeplEngine(name).isPresent()) {
            throw new IllegalArgumentException("The given engine name is already used: " + name);
        }
        RemoteDeplEngine remoteDeplEngine = new RemoteDeplEngine(name, remoteHostAddress);
        engines.add(remoteDeplEngine);
        return remoteDeplEngine;
    }

    public DeplEngine findOrCreateRemoteEngine(String name, String remoteAddress) {
        return getDeplEngine(name).orElse(createRemoteEngine(name, remoteAddress));
    }

    public boolean disconnectEngine(String name) {
        Optional<DeplEngine> deplEngine = getDeplEngine(name);
        if(deplEngine.isPresent()) {
            DeplEngine engine = deplEngine.get();
            if(engine instanceof RemoteDeplEngine)
                return engines.remove(engine);
            else
                return false;
        }
        else {
            return false;
        }
    }

}
