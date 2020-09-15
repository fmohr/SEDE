package ai.services.execution.operator.local;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import ai.services.composition.graphs.nodes.INotification;
import ai.services.composition.graphs.nodes.IWaitForNotificationNode;

public class WaitForNtfOp extends MainTaskOperator {

    public WaitForNtfOp() {
        super(IWaitForNotificationNode.class);
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        IWaitForNotificationNode node = (IWaitForNotificationNode) t.getNode();
        INotification notification = node.getNotification();
        if(t.getFieldContext().hasNotification(notification)) {
            return mainTaskPerformed(t);
        } else {
            return TaskTransition.waitForNtf(notification);
        }
    }

}
