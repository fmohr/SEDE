package ai.services.executor;

import ai.services.execution.GraphTaskExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A registry where one can atomically create and get execution instances.
 * One executor usually has a single execution registry.
 */
public class ExecutionRegistry {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionRegistry.class);

    private Map<String, GraphTaskExecution> execs = new HashMap<>();

    protected boolean isClosed = false;

    private void assertNoClosed() {
        if(isClosed)
            throw new IllegalStateException("Execution registry is closed.");
    }

    public void close() {
        logger.info("Execution registry closed.");
        isClosed = true;
    }

    public synchronized GraphTaskExecution create(String execId) {
        if(execs.containsKey(execId)) {
            throw new IllegalStateException(String.format("Execution `%s` already exists.", execId));
        } else {
            return internalCreate(execId);
        }
    }

    /**
     * Returns an execution if it is already created and not removed.
     * If the the execution doesn't exist it will be created and then returned.
     * This method will always return a valid execution.
     *
     * @deprecated the atomic create or get should not be used anymore.
     * There is only a single time when a execution should be created and that in the case of a execRequest and then it should just be created and an error should be cought,
     */
    @Deprecated
    public synchronized GraphTaskExecution createOrGet(String execId) {
        if(execs.containsKey(execId)) {
            return execs.get(execId);
        } else {
            return internalCreate(execId);
        }
    }

    public synchronized Optional<GraphTaskExecution> get(String execId) {
        return Optional.ofNullable(execs.getOrDefault(execId, null));
    }

    public synchronized Iterator<GraphTaskExecution> iterate() {
        return execs.values().iterator();
    }

    public synchronized void removeIf(Predicate<GraphTaskExecution> executionsToBeRemovedPredicate) {
        execs.values().removeIf(executionsToBeRemovedPredicate);
    }

    public synchronized boolean remove(String execId) {
        return execs.remove(execId) != null;
    }

    private GraphTaskExecution internalCreate(String execId) {
        assertNoClosed();
        GraphTaskExecution execution = new GraphTaskExecution(execId);
        execs.put(execId, execution);
        logger.info("Defined a new execution.");
        return execution;
    }

}
