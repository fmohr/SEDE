package de.upb.sede.edd.deploy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.upb.sede.edd.deploy.DeploymentException;
import de.upb.sede.edd.deploy.DeploymentOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DeploymentSpecificationRegistry extends ArrayList<DeploymentSpecification> {

    @JsonIgnore
    private DeploymentOrder deploymentOrder = new DeploymentOrder(this);

    public Optional<DeploymentSpecification> findByName(String serviceCollectionName) {
        for(DeploymentSpecification spec : this) {
            if(spec.test(serviceCollectionName)) {
                return Optional.of(spec);
            }
        }
        return Optional.empty();
    }
    public Optional<DeploymentSpecification> findByService(String serviceName) {
        for(DeploymentSpecification spec : this) {
            if(spec.getServices().contains(serviceName)) {
                return Optional.of(spec);
            }
        }
        return Optional.empty();
    }

    public Set<DeploymentSpecification> collect(List<String> names, boolean serviceNamesIncluded)  {
        return names.stream()
            .map(name -> // convert to list of specifications
                {
                    Optional<DeploymentSpecification> spec = findByName(name);
                    if(!spec.isPresent() && serviceNamesIncluded) {
                        spec = findByService(name);
                    }
                    return spec.orElseThrow(() ->
                        new DeploymentException(
                        String.format("Cannot find specification for name %s.", name)));
                })
            .collect(Collectors.toSet());
    }

    public DeploymentOrder order() {
        return deploymentOrder;
    }
}
