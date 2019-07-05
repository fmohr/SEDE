package de.upb.sede.edd.deploy.deplengine;

import de.upb.sede.edd.deploy.AscribedService;

import java.util.List;

public abstract class DeplEngine {

    public abstract String getName();

    public abstract void addServices(List<AscribedService> services);

    public abstract void prepareDeployment(boolean update);

    public abstract List<InstallationState> getCurrentState();

    public abstract void removeServices(List<AscribedService> services);

}
