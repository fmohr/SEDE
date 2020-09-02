package ai.services.execution;

import ai.services.execution.local.GraphOperator;
import ai.services.execution.operator.TaskDispatchContainer;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.ICompositionGraph;
import ai.services.execution.operator.GraphDependencyOperator;
import de.upb.sede.composition.graphs.nodes.INotification;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.IExecutorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Encapsulates a single execution with all tasks.
 * Each execution on each executor has exactly one of these instances.
 *
 */
public class GraphTaskExecution implements FieldContext, TaskDispatchContainer {

    private static final Logger logger = LoggerFactory.getLogger(GraphTaskExecution.class);

    private boolean isRunning = false;

    private boolean isInterrupted = false;

    private final String executionId;

    private final Set<Task> finalTasks = new HashSet<>();

    private final Set<Task> queuedTask = new HashSet<>();

    private final Set<Task> waitingTasks = new HashSet<>();

    private final Set<TaskDispatch> runningTaskDispatches = new HashSet<>();

    private final Map<String, SEDEObject> context = new HashMap<>();

    private final Set<INotification> ntfPool = new HashSet<>();

    public GraphTaskExecution(String executionId) {
        this.executionId = executionId;
    }

    public synchronized void addGraph(ICompositionGraph graph) {
        List<BaseNode> nodes = graph.getNodes();
        Map<String, List<Long>> dependencies = graph.getEdges();

        final Map<Long, Task> allTasks = new HashMap<>();

        for (BaseNode node : nodes) {
            Task task = new Task(this, node);
            allTasks.put(node.getIndex(), task);
        }

        final Map<Long, Set<Long>> backwardDependencies = new HashMap<>();

        dependencies.forEach( (indexStr, forwardDependencyList) -> {
            Long index = Long.parseLong(indexStr);
            for (Long forwardTaskIndex : forwardDependencyList) {
                backwardDependencies.computeIfAbsent(forwardTaskIndex, k -> new HashSet<>())
                        .add(index);
            }
        });

        backwardDependencies.forEach((taskIndex, dependencyIndexSet) -> {
            Task task = allTasks.get(taskIndex);
            Set<Task> dependencyTaskSet = new HashSet<>(dependencyIndexSet.size());
            GraphDependencyOperator op = new GraphDependencyOperator(task, dependencyTaskSet);
            for (Long depIndex : dependencyIndexSet) {
                Task dep = allTasks.get(depIndex);
                dependencyTaskSet.add(dep);
                dep.addGraphOperation(op);
            }
        });

        FinalTaskOperation finalTaskOperation = new FinalTaskOperation();
        allTasks.forEach((taskIndex, task)-> {
            if(!backwardDependencies.containsKey(taskIndex)) {
                task.set(Task.State.QUEUED);
                enqueueTask(task);
            }
            // TODO only add FinalNodes to this set
            task.addGraphOperation(finalTaskOperation);
            finalTasks.add(task);
        });
    }

    public synchronized void startExecution() {
        isRunning = true;
    }

    public String getExecutionId() {
        return executionId;
    }

    public synchronized boolean isFinished() {
        return finalTasks.isEmpty();
    }

    public synchronized boolean isInterrupted() {
        return isInterrupted;
    }

    public synchronized void interruptExecution() {
        this.isInterrupted = true;
        this.runningTaskDispatches.forEach(TaskDispatch::interrupt);
    }

    public synchronized Optional<Task> takeNextWaitingTask(WorkerProfile workerProfile) {
        if(!isRunning) {
            return Optional.empty();
        }
        if (!queuedTask.isEmpty()) {
            Iterator<Task> it = queuedTask.iterator();

            while(it.hasNext()) {
                Task queuedTask = it.next();
                if (queuedTask.getWorkerGroup().stream().anyMatch(workerProfile::isInGroup)) {
                    it.remove();
                    return Optional.of(queuedTask);
                } else if (logger.isDebugEnabled()) {
                    logger.debug("Worker profile `{}` didn't match task: {}", workerProfile, queuedTask);
                }
            }
            logger.info("No task in queue of `{}`.", executionId);
        }
        if(requeueWaitingTasks()) {
            return takeNextWaitingTask(workerProfile);
        } else {
            return Optional.empty();
        }
    }

    private synchronized boolean requeueWaitingTasks() {
        Iterator<Task> it = waitingTasks.iterator();
        boolean taskFound = false;
        while (it.hasNext()) {
            Task waitingTask = it.next();
            boolean hasToWait = waitingTask.getWaitingCondition().test(waitingTask);
            if (!hasToWait) {
                it.remove();
                waitingTask.set(Task.State.QUEUED);
                queuedTask.add(waitingTask);
                taskFound = true;
            }
        }
        if(taskFound)
            logger.info("{} many tasks were re-queued from park after their.", queuedTask.size());
        return taskFound;
    }

    public synchronized void parkTask(Task task) {
        waitingTasks.add(task);
    }

    public synchronized void enqueueTask(Task task) {
        queuedTask.add(task);
    }

    public synchronized boolean hasField(String fieldname) {
        return context.containsKey(fieldname);
    }

    public synchronized void setFieldValue(String fieldname, SEDEObject value) {
        this.context.put(fieldname, value);
    }

    public synchronized SEDEObject getFieldValue(String fieldname) {
        if(context.containsKey(fieldname))
            return context.get(fieldname);
        else
            throw new IllegalArgumentException("field is unassigned: " + fieldname);
    }

    public synchronized void deleteField(String fieldname) {
        if(context.containsKey(fieldname))
            context.remove(fieldname);
        else
            throw new IllegalArgumentException("field is unassigned: " + fieldname);
    }

    @Override
    public synchronized void pushNotification(INotification ntf) {
        this.ntfPool.add(ntf);
    }

    @Override
    public synchronized boolean hasNotification(INotification ntf) {
        return ntfPool.contains(ntf);
    }

    public synchronized void registerTaskDispatch(TaskDispatch taskDispatch) {
        this.runningTaskDispatches.add(taskDispatch);
    }

    public synchronized void deregisterTaskDispatch(TaskDispatch taskDispatch) {
        this.runningTaskDispatches.remove(taskDispatch);
    }

    static class FinalTaskOperation implements GraphOperator {

        @Override
        public void perform(GraphTaskExecution ex, Task performer) {
            ex.finalTasks.remove(performer);
        }
    }
}