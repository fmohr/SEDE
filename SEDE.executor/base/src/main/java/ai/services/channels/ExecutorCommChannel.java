package ai.services.channels;

import de.upb.sede.composition.graphs.nodes.INotification;
import de.upb.sede.core.SEDEObject;

public interface ExecutorCommChannel {

    void interrupt(String executionId);

    ExecutionNotificationChannel notificationChannel(String executionId);

    ExecutionDownloadChannel downloadChannel(String executionId);

    ExecutionUploadChannel uploadChannel(String executionId);

    DeployExecutionGraphChannel deployGraphChannel(String executionId);

}
