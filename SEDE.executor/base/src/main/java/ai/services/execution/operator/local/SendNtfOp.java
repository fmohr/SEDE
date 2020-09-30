package ai.services.execution.operator.local;

import ai.services.channels.ChannelService;
import ai.services.channels.ExecutorCommChannel;
import ai.services.channels.PushNotificationException;
import ai.services.composition.INtfInstance;
import ai.services.composition.NtfInstance;
import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import ai.services.composition.graphs.nodes.INotification;
import ai.services.composition.graphs.nodes.INotifyNode;
import ai.services.exec.IExecutorContactInfo;

public class SendNtfOp extends MainTaskOperator {

    private final ChannelService channelService;

    public SendNtfOp(ChannelService channelService) {
        super(true, INotifyNode.class);
        this.channelService = channelService;
    }

    @Override
    public TaskTransition runTask(Task t) throws PushNotificationException {
        INotifyNode notifyNode = (INotifyNode) t.getNode();
        String executionId = t.getFieldContext().contextIdentifier();
        IExecutorContactInfo contactInfo = notifyNode.getContactInfo();
        INotification notification = notifyNode.getNotification();

        ExecutorCommChannel executorCommChannel = channelService.interExecutorCommChannel(contactInfo);
        boolean dependencyFailed = t.isDependencyFailed();
        INtfInstance notifyRequest = NtfInstance.builder()
            .from(notification)
            .executionId(executionId)
            .isSuccessfulNotification(!dependencyFailed)
            .build();
        executorCommChannel.pushNotification(notifyRequest);
        if(dependencyFailed) {
           return TaskTransition.error(new Exception("Dependency failed"));
        }
        return mainTaskPerformed(t);
    }
}
