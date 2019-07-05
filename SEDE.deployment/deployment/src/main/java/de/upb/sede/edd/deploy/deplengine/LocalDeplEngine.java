package de.upb.sede.edd.deploy.deplengine;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.deploy.AscribedService;
import de.upb.sede.edd.deploy.SpecURI;
import de.upb.sede.edd.deploy.specsrc.SpecSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalDeplEngine extends DeplEngine {

    private final EDD daemon;

    private List<InstallationEnv> envs = new ArrayList<>();

    public LocalDeplEngine(EDD daemon) {
        this.daemon = daemon;
    }

    @Override
    public String getName() {
        return DeplEngineRegistry.LOCAL_ENGINE_NAME;
    }

    private LockableDir getLocalDir() {
        return daemon.getHome().getLocalDeplDir();
    }

    Optional<InstallationEnv> findEnv(SpecSource specSrc) {
        return envs
            .stream()
            .filter(installationEnv -> installationEnv.getSpecSource().equals(specSrc))
            .findFirst();
    }

    InstallationEnv createEnv(SpecSource specSrc) {
        InstallationEnv env = new InstallationEnv(daemon, specSrc);
        envs.add(env);
        return env;
    }

    InstallationEnv findOrCreateEnv(SpecSource specSrc) {
        return findEnv(specSrc).orElse(createEnv(specSrc));
    }

    @Override
    public void addServices(List<AscribedService> services) {
        for(AscribedService ascribedService : services) {
            SpecURI specUri = ascribedService.getSpecUri();
            SpecSource source = daemon.getSpecSrc().findOrCreate(specUri);
            InstallationEnv installationEnv = findOrCreateEnv(source);
            installationEnv.addRequestedService(ascribedService.getService());
        }
    }

    @Override
    public void prepareDeployment(boolean update) {
        for(InstallationEnv env : envs) {
            env.install(update);
        }
    }

    @Override
    public List<InstallationState> getCurrentState() {
        List<InstallationState> states = new ArrayList<>();
        for(InstallationEnv env : envs) {
            states.addAll(env.getCurrentState());
        }
        return states;
    }

    @Override
    public void removeServices(List<AscribedService> services) {

    }


}
