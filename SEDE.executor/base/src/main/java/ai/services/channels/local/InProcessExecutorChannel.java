package ai.services.channels.local;

import ai.services.channels.*;
import ai.services.composition.IDeployRequest;
import ai.services.composition.INtfInstance;
import ai.services.exec.ExecutionError;
import ai.services.exec.IExecutionError;
import ai.services.execution.GraphTaskExecution;
import ai.services.executor.Executor;
import ai.services.composition.graphs.nodes.ICompositionGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InProcessExecutorChannel implements ExecutorCommChannel {

    private static final Logger logger = LoggerFactory.getLogger(InProcessExecutorChannel.class);

    private final Executor localExecutor;

    public InProcessExecutorChannel(Executor executor) {
        this.localExecutor = Objects.requireNonNull(executor);
    }

    private Executor getExecutor() {
        return localExecutor;
    }

    @Override
    public Optional<Long> testConnectivity() {
        return Optional.of(0L);
    }

    @Override
    public void interrupt(String executionId) {
        boolean executionFound = false;
        executionFound = getExecutor().acq().computeIfPresent(executionId, GraphTaskExecution::interruptExecution);
        if(!executionFound) {
            logMissingExecutor(executionId, "Interrupting");
        }
    }

    @Override
    public void remove(String executionId) {
        boolean executionFound = false;
        executionFound = getExecutor().acq().computeIfPresent(executionId, GraphTaskExecution::setToBeRemoved);
        if(!executionFound) {
            logMissingExecutor(executionId, "Removing");
        }
    }

    @Override
    public void joinExecution(String executionId) throws InterruptedException {
        GraphTaskExecution graphTaskExecution = getExecutor().acq().waitUntilFinished(executionId);
        if(graphTaskExecution == null) {
            logMissingExecutor(executionId, "Joining");
        }
    }

    @Override
    public List<IExecutionError> getErrors(String executionId) {
        final List<IExecutionError> errors = new ArrayList<>();
        boolean executionFound = getExecutor().acq().computeIfPresent(executionId, execution -> {
            errors.addAll(execution.getErrors());
        });
        if(!executionFound) {
            logMissingExecutor(executionId, "Fetching errors from");
            errors.add(ExecutionError.builder().message("No such executor found: " + executionId).build());
        }
        return errors;
    }

    private void logMissingExecutor(String executionId, String taskDescription) {
        logger.error("{} execution with id '{}', on executor with contact info '{}', was not successful:\n No such execution found.",
            taskDescription,
            executionId, getExecutor().contactInfo());
    }

    @Override
    public void pushNotification(INtfInstance notifyRequest) throws PushNotificationException {
        String executionId = notifyRequest.executionId();
        try {
            boolean exFound = getExecutor().acq().computeIfPresent(executionId, execution -> {
                execution.pushNotification(notifyRequest);
            });
            if(!exFound) {
                throw new RuntimeException("No such execution");
            }
        } catch (RuntimeException e) {
            throw new PushNotificationException(String.format("Error pushing notification %s to execution %s on executor %s: %s",
                notifyRequest, executionId, getExecutor().contactInfo(), e.getMessage()));
        }
    }

    @Override
    public DataChannel dataChannel(String executionId) {
        return new InProcessHandOffDataChannel(getExecutor().acq(), executionId);
    }

    @Override
    public void deployGraph(IDeployRequest deployRequest) throws GraphDeploymentException {
        String executionId = deployRequest.executionId();
        ICompositionGraph toBeDeployed = deployRequest.getCompGraph();
        final Executor executor = getExecutor();
        executor.deploy(executionId, toBeDeployed);
    }

    @Override
    public void startExecution(String executionId) {
        final Executor executor = getExecutor();
        boolean executionPresent = executor.acq().computeIfPresent(executionId, GraphTaskExecution::startExecution);
        if(!executionPresent) {
            throw new IllegalStateException("Execution is not present: " + executionId);
        }
    }

}

