package de.upb.sede.edd.deploy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Ordering;
import de.upb.sede.edd.deploy.DeploymentException;
import de.upb.sede.edd.deploy.DeploymentOrder;
import de.upb.sede.edd.deploy.ServiceDeployment;
import de.upb.sede.util.Uncheck;

import java.util.*;
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

    public Set<DeploymentSpecification> collect(Collection<String> names, boolean serviceNamesIncluded, boolean quite)  {
        if(Objects.requireNonNull(names).isEmpty()) {
            return Collections.emptySet();
        }
        return names.stream()
            .map(name -> // convert to list of specifications
                {
                    Optional<DeploymentSpecification> spec = findByName(name);
                    if(!spec.isPresent() && serviceNamesIncluded) {
                        spec = findByService(name);
                    }
                    return spec;
                })
            .filter(optional -> !quite || optional.isPresent())
            .map(optional -> optional.orElseThrow(() ->
                new DeploymentException(
                    String.format("Cannot find all services: %s", names.toString())))) // TODO include not found service in error message.
            .collect(Collectors.toSet());
    }

    public Set<DeploymentSpecification> includeDependencies(Collection<DeploymentSpecification> specs) {
        Set<DeploymentSpecification> allSpecs = new HashSet<>(specs);
        for(DeploymentSpecification spec : specs) {
            recursiveIncludeDependency(allSpecs, spec);
        }
        return allSpecs;
    }

    private void recursiveIncludeDependency(
        Set<DeploymentSpecification> availableSpecs,
        DeploymentSpecification spec) {
        DeploymentMethod method = ServiceDeployment.findMethod(spec);
        for(String dependency : method.getDependencies()) {
            if (availableSpecs.stream().noneMatch(s -> s.test(dependency))) {
                DeploymentSpecification dependencySpec = findByName(dependency)
                    .orElseThrow(()-> new DeploymentException("Cannot find dependency: " + dependency));
                availableSpecs.add(dependencySpec);
                recursiveIncludeDependency(availableSpecs, dependencySpec);
            }
        }
    }

    public Comparator<DeploymentSpecification> order() {
        return Ordering.explicit(deploymentOrder.access()).reversed();
    }

    public static DeploymentSpecificationRegistry fromString(String jsonString) {
        ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return Uncheck.call(() -> mapper.readValue(jsonString, DeploymentSpecificationRegistry.class));
    }
}
