package ai.services.channels;

import de.upb.sede.composition.graphs.nodes.ICompositionGraph;

public interface DeployExecutionGraphChannel {

    void deployGraph(ICompositionGraph toBeDeployed);

}
