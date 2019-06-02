package de.upb.sede.edd.deploy;

import de.upb.sede.edd.deploy.model.DeploymentSpecification;
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.Cache;
import de.upb.sede.util.GPDirectedGraph;
import de.upb.sede.util.LazyAccessCache;

import java.util.List;
import java.util.stream.Collectors;

public class DeploymentOrder extends LazyAccessCache<List<DeploymentSpecification>> implements Cache<List<DeploymentSpecification>> {

    public DeploymentOrder(DeploymentSpecificationRegistry registry) {
        super(() ->
        {
            GPDirectedGraph<DeploymentSpecification, DeploymentSpecification> graph;
            graph = new GPDirectedGraph<DeploymentSpecification, DeploymentSpecification>(registry,
                (DeploymentSpecification deployment) -> {
                    List<DeploymentSpecification> dependencies;
                    dependencies = ServiceDeployment.findMethod(deployment).getDependencies()
                        .stream()
                        .map(dependencyName ->
                            registry.findByName(dependencyName).orElseThrow(() ->
                                new DeploymentException(
                                    String.format("Cannot find dependency of %s named %s.",
                                        deployment.getName(),
                                        dependencyName))))
                        .collect(Collectors.toList());
                    return dependencies;
                });
            if(graph.isDAG()) {
                return graph.topologicalSort();
            } else {
                throw new DeploymentException(
                    String.format("Deployment has a dependency cycle: %s", graph.cycle().toString()));
            }
        });
    }
}
