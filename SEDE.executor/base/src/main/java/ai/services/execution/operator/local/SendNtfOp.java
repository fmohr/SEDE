package ai.services.execution.operator.local;

import ai.services.channels.ChannelService;
import ai.services.channels.ExecutorCommChannel;
import ai.services.channels.PushNotificationException;
import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import de.upb.sede.composition.graphs.nodes.INotification;
import de.upb.sede.composition.graphs.nodes.INotifyNode;
import de.upb.sede.exec.IExecutorContactInfo;

public class SendNtfOp extends MainTaskOperator {

    private final ChannelService channelService;

    public SendNtfOp(ChannelService channelService) {
        super(INotifyNode.class);
        this.channelService = channelService;
    }

    @Override
    public TaskTransition runTask(Task t) throws PushNotificationException {
        INotifyNode notifyNode = (INotifyNode) t.getNode();
        String executionId = t.getFieldContext().contextIdentifier();
        IExecutorContactInfo contactInfo = notifyNode.getContactInfo();
        INotification notification = notifyNode.getNotification();

        ExecutorCommChannel executorCommChannel = channelService.interExecutorCommChannel(contactInfo);
        executorCommChannel.pushNotification(executionId, notification);

        return mainTaskPerformed(t);
    }
}
