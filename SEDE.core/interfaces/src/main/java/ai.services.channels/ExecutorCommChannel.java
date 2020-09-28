package ai.services.channels;

import ai.services.composition.IDeployRequest;
import ai.services.composition.INtfInstance;

import java.util.Optional;

public interface ExecutorCommChannel {

    Optional<Long> testConnectivity();

    void interrupt(String executionId);

    void pushNotification(INtfInstance notifyRequest) throws PushNotificationException;

    ExecutionDataChannel dataChannel(String executionId);

    void deployGraph(IDeployRequest deployRequest) throws GraphDeploymentException;

    void startExecution(String executionId);

    void remove(String executionId);
}
