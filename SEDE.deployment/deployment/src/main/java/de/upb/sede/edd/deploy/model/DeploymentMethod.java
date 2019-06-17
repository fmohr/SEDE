package de.upb.sede.edd.deploy.model;

import de.upb.sede.edd.deploy.AcrPath;
import de.upb.sede.util.KneadableField;
import de.upb.sede.util.Validatable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeploymentMethod implements Validatable {

    private String methodType = "";

    private List<KneadableField> sources    =   new ArrayList<>();

    private List<KneadableField> builds     =   new ArrayList<>();

    private List<KneadableField> deployments = new ArrayList<>();

    private List<KneadableField> actions = new ArrayList<>();

    private List<String> dependencies = new ArrayList<>();

    public String getMethodType() {
        return methodType == null? "" : methodType;
    }

    public List<KneadableField> getSources() {
        return sources;
    }

    public List<KneadableField> getBuilds() {
        return builds;
    }

    public List<KneadableField> getDeployments() {
        return deployments;
    }

    public List<KneadableField> getActions() {
        return actions;
    }


    public List<String> getDependencies() {
        return dependencies;
    }

    public void setMethodType(String methodtype) {
        this.methodType = methodtype;
    }

    public void setSources(List<KneadableField> sources) {
        this.sources = sources;
    }

    public void setBuilds(List<KneadableField> builds) {
        this.builds = builds;
    }

    public void setDeployments(List<KneadableField> deployments) {
        this.deployments = deployments;
    }

    public void setActions(List<KneadableField> actions) {
        this.actions = actions;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public void validate() throws RuntimeException {
        Set<String> sourceDirAcrs = new HashSet<>();
        for(KneadableField source : getSources()) {
            boolean newDirName = sourceDirAcrs.add(source.knead(DeploymentSourceDirAcr.class).getDirectoryAcronym());
            if(!newDirName) {
                throw new RuntimeException("Source directory with name " + source.knead(DeploymentSourceDirAcr.class) + " was specified multiple times.");
            }
        }
    }
}
