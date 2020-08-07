package de.upb.sede.edd;

import de.upb.sede.util.FileCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.upb.sede.edd.EDDVersion.Version.*;

public class EDDVersion {

    private static final Logger logger = LoggerFactory.getLogger(EDDVersion.class);

    private EDDHome home;

    private FileCache<Version> currentVersion;

    private static final Version LATEST;

    static {
        Version latest = null;
        for(Version v : Version.values()) {
            if(v.isLatest()) {
                latest = v;
            }
        }
        LATEST = latest;
    }

    enum Version {
        v_0_1(null, "v.0.1"),
        v_0_0(v_0_1, "v.0.0");

        Version nextVersion;
        String displayText;

        Version(Version next, String displayText) {
            this.nextVersion = next;
            this.displayText = displayText;
        }

        boolean isLatest() {
            return nextVersion == null;
        }

        static Version decode(String text) {
            try {
                return Version.valueOf(text);
            }
            catch (NullPointerException ex) {
                return v_0_0;
            }
            catch (IllegalArgumentException ex ) {
                logger.warn("Unkown EDD Version was loaded: " + text);
                return v_0_0;
            }
        }


        @Override
        public String toString() {
            return displayText;
        }
    }

    public EDDVersion(EDDHome home) {
        this.home = home;
        this.currentVersion = new FileCache<>(home.getHomeDir().subFile("edd.version"),
            Version::decode, Version::name);

    }

    public void migrateToLatest() {
        Version currentVersion = this.currentVersion.access();
        if(currentVersion.isLatest()) {
            logger.info("Local EDD version is the latest known version {}.", currentVersion);
            return;
        }
        logger.info("EDD version migration started. Current version is {}. Latest version is {}.", currentVersion, LATEST);
        while(!currentVersion.isLatest()) {
            currentVersion = migrateToNextVersion(currentVersion);
        }

        this.currentVersion.set(currentVersion);
        logger.info("EDD version migration completed. The version was saved in {}.", this.currentVersion.getFile());
    }

    private Version migrateToNextVersion(Version currentVersion) {
        switch(currentVersion) {
            case v_0_0: return migrateV_0_0();
            // TODO add a case for each new version. Make sure there is no loop and each step return a newer one.
            default: return LATEST;
        }
    }

    private Version migrateV_0_0() {
        logger.info("Migrating from {} to {}...", v_0_0, v_0_1);
        logger.info("Removing groups..");
        LockableDir dir;
        dir = home.getHomeDir().getChild("groups");
        dir.clear();
        dir.toFile().delete();


        logger.info("Removing service instances..");

        dir = home.getHomeDir().getChild("serviceinstances");
        dir.clear();
        dir.toFile().delete();

        logger.info("Removing deployed services..");

        dir = home.getHomeDir().getChild("services");
        dir.clear();
        dir.toFile().delete();

        logger.info("Removing previous applications..");

        dir = home.getHomeDir().getChild("apps");
        dir.clear();
        dir.toFile().delete();


        return v_0_1;
    }
}
