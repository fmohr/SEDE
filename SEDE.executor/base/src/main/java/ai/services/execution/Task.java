package ai.services.execution;

import de.upb.sede.composition.graphs.nodes.BaseNode;

import java.util.*;
import java.util.function.Predicate;

/**
 * Mutable class that tracks the state of a workload.
 * An execution graph consists of many tasks.
 * These tasks have dependencies that are resolved with graphoperators.
 *
 * An execution graph is considered finished if all tasks in it are either successful or failed.
 */
public class Task {

    public enum State {
        DISABLED, WAITING, QUEUED, RUNNING, SUCCESS, FAILURE;
    }

    private List<String> workerGroup = Collections.singletonList("HOST");

    private BaseNode node;

    private final FieldContext fieldContext;

    private List<GraphOperator> graphOps = new ArrayList<>();

    private Predicate<Task> waitingCondition;

    private Map<Object, Object> workingMem = new HashMap<>();

    private State currentState = State.DISABLED;

    private boolean dependencyFailed = false;

    private boolean mainTaskPerformed = false;

    private Exception error;

    public Task(FieldContext fieldContext, BaseNode node) {
        this.node = node;
        this.fieldContext = fieldContext;
        this.waitingCondition = (task) -> false;
    }

    public Optional<Exception> getError() {
        return Optional.ofNullable(error);
    }

    public BaseNode getNode() {
        return node;
    }

    public boolean isOfType(Class<? extends BaseNode> baseNodeClass) {
        return baseNodeClass == null || baseNodeClass.isInstance(node);
    }

//    private GraphTaskExecution getExecution() {
//        return execution;
//    }

    public FieldContext getFieldContext() {
        return fieldContext;
    }
    public <V> V getWorkingMemVal(Object key) {
        return (V) workingMem.get(key);
    }

    public void putWorkingMemVal(Object key, Object value) {
        workingMem.put(key, value);
    }

    public void setError(Exception exception) {
        this.error = exception;
    }

    @Override
    public String toString() {
        return node.getText();
    }

    public boolean testWaitingCondition() {
        return waitingCondition.test(this);
    }

    void setWaitingCondition(Predicate<Task> waitingCondition) {
        this.waitingCondition = waitingCondition;
    }

    void addWaitingCondition(Predicate<Task> waitingCondition) {
        this.waitingCondition = waitingCondition.or(waitingCondition);
    }

    void addGraphOperation(GraphOperator graphOp) {
        this.graphOps.add(graphOp);
    }

    List<GraphOperator> getGraphOps() {
        return this.graphOps;
    }

    public boolean isDependencyFailed() {
        return dependencyFailed;
    }

    public void setDependencyFailed() {
        this.dependencyFailed = true;
    }

    public boolean isMainTaskPerformed() {
        return mainTaskPerformed;
    }

    public void setMainTaskPerformed() {
        this.mainTaskPerformed = true;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void set(State currentState) {
        this.currentState = currentState;
    }

    public boolean is(State state) {
        return currentState == state;
    }

    public boolean isFinished() {
        return isEither(State.SUCCESS, State.FAILURE);
    }

    public boolean isEither(State... states) {
        Objects.requireNonNull(states);
        assert states.length > 0;
        for (State state : states) {
            if(is(state)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getWorkerGroup() {
        return workerGroup;
    }


}

