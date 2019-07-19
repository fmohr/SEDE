package de.upb.sede.edd;

import de.upb.sede.edd.deploy.deplengine.DeplEngineRegistry;
import de.upb.sede.edd.deploy.specsrc.SpecSourceRegistry;
import de.upb.sede.edd.runtime.LocalRuntimeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EDD {

    private final static Logger logger = LoggerFactory.getLogger(EDD.class);

    private static final EDD instance = new EDD();

    private EDD() {
    }

    public static EDD getInstance() {
        if(!instance.initialized)
            instance.initialize();
        return instance;
    }

    private EDDHome home = new EDDHome();

    private EDDVersion version = new EDDVersion(home);

    private LocalEDDInfo info;

    private SpecSourceRegistry specSrc = new SpecSourceRegistry(home);

    private DeplEngineRegistry deplEngineRegistry = new DeplEngineRegistry(this);

    private LocalRuntimeRegistry runtimeRegistry = new LocalRuntimeRegistry(this);



    private boolean initialized = false;


    private synchronized void initialize() {
        if(initialized) {
            logger.error("EDD was already initialized. Restart the application for reinitialization.");
            return;
        }

        logger.info("EDD initializing...");
        version.migrateToLatest();
        this.initialized = true;
        this.info = new LocalEDDInfo(home);
    }

    public EDDHome getHome() {
        return home;
    }

    public SpecSourceRegistry getSpecSrc() {
        return specSrc;
    }

    public DeplEngineRegistry getDeploymentEngine() {
        return deplEngineRegistry;
    }

    public EDDVersion getVersion() {
        return version;
    }

    public LocalEDDInfo getInfo() {
        return info;
    }

    public LocalRuntimeRegistry getRuntimeRegistry() {
        return runtimeRegistry;
    }
}
