package ai.services.execution.local;

import ai.services.execution.Task;
import ai.services.execution.Execution;

/**
 * This operator is applied on the graph.
 * It is bounded to task they can do tasks such as:
 *  - Resolve task dependencies
 *  - Control structures such as: If, Else, Loops
 *  - Block calls like: subroutine invocation
 */
public interface GraphOperator {

    void perform(Execution ex, Task performer);

}
