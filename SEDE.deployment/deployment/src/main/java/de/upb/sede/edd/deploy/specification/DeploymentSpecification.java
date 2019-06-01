package de.upb.sede.edd.deploy.specification;

import java.util.ArrayList;
import java.util.List;

public class DeploymentSpecification {
    private String name;
    private List<String> alias = new ArrayList<>();
    private List<String> services = new ArrayList<>();
    private String baseExecutor;
    private List<DeploymentMethod> methods = new ArrayList<>();

    public boolean validate() {
        return name != null  && baseExecutor != null;
    }

    @Override
    public String toString() {
        return "DeploymentSpecification{" +
            "name='" + name + '\'' +
            ", baseExecutor='" + baseExecutor + '\'' +
            '}';
    }

    public String getName() {
        return name;
    }

    public List<String> getAlias() {
        return alias;
    }

    public List<String> getServices() {
        return services;
    }

    public String getBaseExecutor() {
        return baseExecutor;
    }

    public List<DeploymentMethod> getMethods() {
        return methods;
    }
}
