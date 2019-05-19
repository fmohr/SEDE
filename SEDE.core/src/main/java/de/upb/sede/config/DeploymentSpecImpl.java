package de.upb.sede.config;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class DeploymentSpecImpl implements DeploymentSpec {

    private final List<String> aliases;

    private final List<String> services;

    private final List<DeploymentMethod> methods;

    @JsonCreator
    public DeploymentSpecImpl(List<String> aliases, List<String> services, List<DeploymentMethod> methods) {
        this.aliases = aliases;
        this.services = services;
        this.methods = methods;
    }

    @Override
    public Collection<String> getAliases() {
        return unmodifiableList(aliases);
    }

    @Override
    public Collection<String> getServices() {
        return unmodifiableList(services);
    }

    @Override
    public List<DeploymentMethod> getDeploymentMethods() {
        return unmodifiableList(methods);
    }
}
