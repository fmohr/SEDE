package ai.services.channels;

public interface ExecutorCommChannel {

    void interrupt(String executionId);

    ExecutionNotificationChannel notificationChannel(String executionId);

    ExecutionDownloadChannel downloadChannel(String executionId);

    ExecutionUploadChannel uploadChannel(String executionId);

    DeployExecutionGraphChannel deployGraphChannel(String executionId);

}
