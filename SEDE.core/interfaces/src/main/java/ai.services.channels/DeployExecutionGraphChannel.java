package ai.services.channels;

import ai.services.composition.graphs.nodes.ICompositionGraph;

public interface DeployExecutionGraphChannel {

    void deployGraph(ICompositionGraph toBeDeployed) throws GraphDeploymentException;

}
