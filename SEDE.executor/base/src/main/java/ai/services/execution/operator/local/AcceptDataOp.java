package ai.services.execution.operator.local;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.IAcceptDataNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcceptDataOp extends MainTaskOperator {

    private static final Logger logger = LoggerFactory.getLogger(InstructionOp.class);

    public AcceptDataOp() {
        super(IAcceptDataNode.class);
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        IAcceptDataNode node = (IAcceptDataNode) t.getNode();
        String fieldname = node.getFieldName();
        boolean fieldPresent = t.getFieldContext().hasField(fieldname);
        if(fieldPresent) {
            return mainTaskPerformed(t);
        } else {
            return TaskTransition.waitForField(fieldname);
        }
    }


}
