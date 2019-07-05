package de.upb.sede.edd.deploy.model;

import de.upb.sede.util.DynTypeField;
import de.upb.sede.util.Validatable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeploymentMethod implements Validatable {

    private String methodType = "";

    private List<DynTypeField> sources    =   new ArrayList<>();

    private List<DynTypeField> builds     =   new ArrayList<>();

    private List<DynTypeField> deployments = new ArrayList<>();

    private List<DynTypeField> actions = new ArrayList<>();

    private List<String> dependencies = new ArrayList<>();

    public String getMethodType() {
        return methodType == null? "" : methodType;
    }

    public List<DynTypeField> getSources() {
        return sources;
    }

    public List<DynTypeField> getBuilds() {
        return builds;
    }

    public List<DynTypeField> getDeployments() {
        return deployments;
    }

    public List<DynTypeField> getActions() {
        return actions;
    }


    public List<String> getDependencies() {
        return dependencies;
    }

    public void setMethodType(String methodtype) {
        this.methodType = methodtype;
    }

    public void setSources(List<DynTypeField> sources) {
        this.sources = sources;
    }

    public void setBuilds(List<DynTypeField> builds) {
        this.builds = builds;
    }

    public void setDeployments(List<DynTypeField> deployments) {
        this.deployments = deployments;
    }

    public void setActions(List<DynTypeField> actions) {
        this.actions = actions;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public void validate() throws RuntimeException {
        Set<String> sourceDirAcrs = new HashSet<>();
        for(DynTypeField source : getSources()) {
            boolean newDirName = sourceDirAcrs.add(source.cast(DeploymentSourceDirName.class).getDirectoryName());
            if(!newDirName) {
                throw new RuntimeException("Source directory with name " + source.cast(DeploymentSourceDirName.class) + " was specified multiple times.");
            }
        }
    }
}
