package de.upb.sede.config;

import java.util.Collection;
import java.util.List;

public interface DeploymentSpec {

    Collection<String> getAliases();

    Collection<String> getServices();

    List<DeploymentMethod> getDeploymentMethods();

}
