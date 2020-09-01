package ai.services.execution.operator;

import ai.services.execution.TaskDispatch;

public interface TaskDispatchContainer {

    void registerTaskDispatch(TaskDispatch taskDispatch);

    void deregisterTaskDispatch(TaskDispatch taskDispatch);

}
