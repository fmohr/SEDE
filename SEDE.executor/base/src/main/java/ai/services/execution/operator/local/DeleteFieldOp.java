package ai.services.execution.operator.local;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import ai.services.composition.graphs.nodes.IDeleteFieldNode;

public class DeleteFieldOp extends MainTaskOperator {
    public DeleteFieldOp() {
        super(IDeleteFieldNode.class);
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        IDeleteFieldNode node = (IDeleteFieldNode) t.getNode();
        String fieldname = node.getFieldName();
        t.getFieldContext().deleteField(fieldname);
        return mainTaskPerformed(t);
    }
}
