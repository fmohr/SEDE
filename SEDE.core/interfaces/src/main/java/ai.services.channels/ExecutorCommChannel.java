package ai.services.channels;

import ai.services.composition.IDeployRequest;
import ai.services.composition.INotifyRequest;
import ai.services.composition.graphs.nodes.ICompositionGraph;
import ai.services.composition.graphs.nodes.INotification;

import java.util.Optional;

public interface ExecutorCommChannel {

    Optional<Long> testConnectivity();

    void interrupt(String executionId);

    void pushNotification(INotifyRequest notifyRequest) throws PushNotificationException;

    ExecutionDataChannel dataChannel(String executionId);

    void deployGraph(IDeployRequest deployRequest) throws GraphDeploymentException;

    void remove(String executionId);
}
