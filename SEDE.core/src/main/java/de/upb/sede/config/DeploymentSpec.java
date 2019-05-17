package de.upb.sede.config;

import java.util.List;

public interface DeploymentSpec {

    public boolean isSpecified(String serviceCollection);

    public List<String> serviceCollectionList();


}
