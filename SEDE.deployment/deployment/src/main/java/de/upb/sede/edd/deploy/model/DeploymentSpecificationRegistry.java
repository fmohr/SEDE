package de.upb.sede.edd.deploy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Ordering;
import de.upb.sede.edd.deploy.DeploymentException;
import de.upb.sede.edd.deploy.ServiceDeployment;

import java.util.*;
import java.util.stream.Collectors;

public class DeploymentSpecificationRegistry implements Validatable {

    @JsonIgnore
    private Cache<Comparator<DeploymentSpecification>> deploymentOrder =
        new LazyAccessCache<>(this::doOrder);

    private List<DeploymentSpecification> specs = new ArrayList<>();

    public DeploymentSpecificationRegistry(List<DeploymentSpecification> specs) {
        this.specs = specs;
    }

    public DeploymentSpecificationRegistry() {
    }


    public List<DeploymentSpecification> getSpecs() {
        return Collections.unmodifiableList(specs);
    }

    public void setSpecs(List<DeploymentSpecification> specs){
        this.specs = specs;
    }

    @Override
    public void validate() throws RuntimeException {
        if(specs == null) {
            throw new IllegalStateException("No spec was defined.");
        }
    }


    public Optional<DeploymentSpecification> findByName(String serviceCollectionName) {
        for(DeploymentSpecification spec : specs) {
            if(spec.test(serviceCollectionName)) {
                return Optional.of(spec);
            }
        }
        return Optional.empty();
    }
    public Optional<DeploymentSpecification> findByService(String serviceName) {
        for(DeploymentSpecification spec : specs) {
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

    public static DeploymentSpecificationRegistry fromString(String jsonString) {
        ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType type_ = mapper.getTypeFactory().constructCollectionType(ArrayList.class, DeploymentSpecification.class);
        List<DeploymentSpecification> specs =  Uncheck.call(
            () -> mapper.readValue(jsonString, type_)
        );
        return new DeploymentSpecificationRegistry(specs);
    }


    public Comparator<DeploymentSpecification> getDependencyOrder() {
        return deploymentOrder.get();
    }

    private Ordering<DeploymentSpecification> doOrder() {
        GPDirectedGraph<DeploymentSpecification, DeploymentSpecification> graph;
        graph = new GPDirectedGraph<DeploymentSpecification, DeploymentSpecification>(this.getSpecs(),
            (DeploymentSpecification deployment) -> {
                List<DeploymentSpecification> dependencies;
                dependencies = ServiceDeployment.findMethod(deployment).getDependencies()
                    .stream()
                    .map(dependencyName ->
                        this.findByName(dependencyName).orElseThrow(() ->
                            new DeploymentException(
                                String.format("Cannot find dependency of %s named %s.",
                                    deployment.getName(),
                                    dependencyName))))
                    .collect(Collectors.toList());
                return dependencies;
            });
        if(graph.isDAG()) {
            return Ordering.explicit(graph.topologicalSort()).reverse();
        } else {
            throw new IllegalStateException(
                String.format("Deployment has a dependency cycle: %s", graph.cycle().toString()));
        }
    }
}
