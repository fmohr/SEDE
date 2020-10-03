package ai.services.execution.operator.local;

import ai.services.composition.graphs.nodes.INopNode;
import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;

public class NopOp extends MainTaskOperator {


    public NopOp() {
        super(INopNode.class);
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        return mainTaskPerformed(t);
    }
}
