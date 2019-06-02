package de.upb.sede.edd.deploy.model;

import de.upb.sede.util.KneadableField;

import java.util.ArrayList;
import java.util.List;

public class DeploymentMethod {

    private String method = "";

    private List<KneadableField> sources    =   new ArrayList<>();

    private List<KneadableField> builds     =   new ArrayList<>();

    private List<KneadableField> deployments = new ArrayList<>();

    private List<KneadableField> actions = new ArrayList<>();

    private List<String> dependencies = new ArrayList<>();

    private List<String> output = new ArrayList<>();

    public String getMethod() {
        return method == null? "" : method;
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

    public List<String> getOutput() {
        return output;
    }

    public List<String> getDependencies() {
        return dependencies;
    }
}
