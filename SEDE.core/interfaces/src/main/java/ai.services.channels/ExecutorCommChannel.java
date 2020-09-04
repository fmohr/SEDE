package ai.services.channels;

import de.upb.sede.composition.graphs.nodes.ICompositionGraph;
import de.upb.sede.composition.graphs.nodes.INotification;

import java.util.Optional;

public interface ExecutorCommChannel {

    Optional<Long> testConnectivity();

    void interrupt(String executionId);

    void pushNotification(String executionId, INotification notification) throws PushNotificationException;

    ExecutionDataChannel dataChannel(String executionId);

    void deployGraph(String executionId, ICompositionGraph toBeDeployed) throws GraphDeploymentException;

}
