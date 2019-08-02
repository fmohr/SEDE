package de.upb.sede.edd.deploy;

import de.upb.sede.util.DynType;

import java.io.File;

public abstract class AbstractSourceDeployment implements EDDSource {

    private String displayName;
    private File sourceHome;
    private DynType sourceDefinition;

    public AbstractSourceDeployment(String displayName, File sourceHome, DynType sourceDefinition) {
        this.displayName  = displayName;
        this.sourceHome = sourceHome;
        this.sourceDefinition = sourceDefinition;
    }

    public File getSourceHome() {
        return sourceHome;
    }

    public DynType getSourceDefinition() {
        return sourceDefinition;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public final boolean retrieve(boolean update, ServiceDeployment currentDeployment) {
        try {
            getSourceHome().mkdirs();
            if(! getSourceHome().isDirectory()) {
                throw new DeploymentException(displayName + " cannot create source folder.");
            }
            return retrieveSource(update);
        } catch (DeploymentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DeploymentException(displayName + " error retrieving sources: " + getSourceDefinition().toString(), ex);
        }
    }

    protected abstract boolean retrieveSource(boolean update) throws Exception;
}