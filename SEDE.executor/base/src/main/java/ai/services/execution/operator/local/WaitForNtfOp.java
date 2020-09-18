package ai.services.execution.operator.local;

import ai.services.composition.INotifyRequest;
import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import ai.services.composition.graphs.nodes.INotification;
import ai.services.composition.graphs.nodes.IWaitForNotificationNode;

import java.util.Optional;

public class WaitForNtfOp extends MainTaskOperator {

    public WaitForNtfOp() {
        super(IWaitForNotificationNode.class);
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        IWaitForNotificationNode node = (IWaitForNotificationNode) t.getNode();
        INotification notification = node.getNotification();
        if(t.getFieldContext().hasNotification(notification)) {
            Optional<INotification> contextNtfOpt = t.getFieldContext().getNotification(notification);
            INotification contextNtf = contextNtfOpt.get();
            if(contextNtf instanceof INotifyRequest) {
                if(!((INotifyRequest) contextNtf).isSuccessfulNotification()) {
                    return TaskTransition.error(
                        new RuntimeException("Pushed notification is not marked successful"));
                }
            }
            return mainTaskPerformed(t);
        } else {
            return TaskTransition.waitForNtf(notification);
        }
    }

}
