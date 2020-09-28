package ai.services;

import ai.services.channels.ChannelService;
import ai.services.channels.ExecutorCommChannel;
import ai.services.composition.DeployRequest;
import ai.services.composition.graphs.nodes.ICompositionGraph;
import ai.services.core.SEDEObject;
import ai.services.execution.ExecutionState;
import ai.services.execution.IExecutionState;
import ai.services.requests.resolve.beta.IChoreography;
import ai.services.requests.resolve.beta.IResolveRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class ExecutionController implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionController.class);

    private final ChannelService channelService;

    private final IResolveRequest resolveRequest;

    private final IChoreography choreography;

    private String executionId;

    private final Map<String, IExecutionState> executionStates = new HashMap<>();

    private AtomicBoolean closeFlag = new AtomicBoolean(false);

    private AtomicBoolean startedFlag = new AtomicBoolean(false);

    private AtomicBoolean finishedFlag = new AtomicBoolean(false);

    public ExecutionController(ChannelService channelService, IResolveRequest resolveRequest, IChoreography choreography) {
        this.channelService = channelService;
        this.resolveRequest = resolveRequest;
        this.choreography = choreography;
    }

    public String getExecutionId() {
        if(executionId != null) {
            return executionId;
        }
        synchronized (this) {
            if(executionId == null) {
                executionId = UUID.randomUUID().toString();
            }
        }
        return executionId;
    }

    public synchronized void startExecution() {
        if(closeFlag.get()) {
            throw new IllegalStateException(String.format("Execution '%s' is closed.", getExecutionId()));
        }
        if(startedFlag.get()) {
            throw new IllegalStateException(String.format("Execution '%s' is already started.", getExecutionId()));
        }
        startedFlag.set(true);
        // testConnectivity();
        deploy();
        start();
    }

    private void deploy() {
        forEachExecutor((comp, channel) -> {
            try {
                executionStates.compute(comp.getExecutorHandle().getQualifier(), this::setDeployed);
                ExecutorCommChannel executorCommChannel = channelService.interExecutorCommChannel(comp.getExecutorHandle().getContactInfo());
                executorCommChannel.deployGraph(DeployRequest.builder()
                    .compGraph(comp)
                    .executionId(getExecutionId())
                    .build());
            } catch (Exception ex) {
                logger.error("Error deploying composition to {}. Error message: {}",
                    comp.getExecutorHandle().getContactInfo(),
                    ex.getMessage());
                close();
                throw new ExecutionRuntimeException(ex);
            }
        });
    }

    private IExecutionState setDeployed(String executionId, IExecutionState oldState) {
        ExecutionState.Builder builder = ExecutionState.builder();
        if(oldState != null) {
            builder.from(oldState);
            if(oldState.isDeployed()) {
                throw new IllegalStateException("Execution was already deployed on " + executionId);
            }
        }
        builder.isDeployed(true);
        return builder.build();
    }

    private void start() {
        forEachExecutor((comp, channel) -> {
            try {
                executionStates.compute(comp.getExecutorHandle().getQualifier(), this::setStarted);
                ExecutorCommChannel executorCommChannel = channelService.interExecutorCommChannel(comp.getExecutorHandle().getContactInfo());
                executorCommChannel.startExecution(getExecutionId());
            } catch (Exception ex) {
                logger.error("Error starting execution on {}. Error message: {}",
                    comp.getExecutorHandle().getContactInfo(),
                    ex.getMessage());
                close();
                throw new ExecutionRuntimeException(ex);
            }
        });
    }

    private IExecutionState setStarted(String executionId, IExecutionState oldState) {
        ExecutionState.Builder builder = ExecutionState.builder();
        if(oldState != null) {
            if(oldState.isStarted()) {
                throw new IllegalStateException("Execution already started on " + executionId);
            }
            builder.from(oldState);
        }
        builder.isStarted(true);
        return builder.build();
    }

    private void assertStarted() {
        if(!startedFlag.get()) {
            throw new IllegalStateException("Not started: " + executionId);
        }
    }

    public void setInitialField(String fieldname, SEDEObject fieldValue) {
        assertStarted();

    }

    public void waitUntilFinished() {
        assertStarted();

        finishedFlag.set(true);
    }

    private void assertFinished() {
        if(!finishedFlag.get()) {
            throw new IllegalStateException("Not finished: " + executionId);
        }
    }

    public SEDEObject getResultField(String fieldname) {
        assertFinished();

    }

    @Override
    public synchronized void close()  {
        closeFlag.set(true);
        interrupt();
        remove();
    }

    public synchronized void interrupt() {
        forEachExecutor(this::interrupt);
    }

    private void interrupt(ICompositionGraph comp, ExecutorCommChannel channel) {
        String executorQualifier = comp.getExecutorHandle().getQualifier();
        IExecutionState executionState = executionStates.get(executorQualifier);
        if(executionState == null || !executionState.isStarted()) {
            logger.info("Didn't interrupt execution {} on executor {}: It was not started .", getExecutionId(), executorQualifier);
            return;
        }
        try {
            channel.interrupt(getExecutionId());
            logger.info("Interrupted execution {} on executor {}.", getExecutionId(), executorQualifier);
        } catch (Exception ex) {
            logger.warn("Error while interrupting execution {} on executor: {}",
                getExecutionId(),
                comp.getExecutorHandle().getContactInfo(),
                ex);
        }
    }



    private void remove() {
        forEachExecutor(this::remove);
    }

    private void remove(ICompositionGraph comp, ExecutorCommChannel channel) {
        String executorQualifier = comp.getExecutorHandle().getQualifier();
        IExecutionState executionState = executionStates.get(executorQualifier);
        if(executionState == null || !executionState.isDeployed()) {
            logger.info("Didn't remove execution {} on executor {}: It was not deployed.", getExecutionId(), executorQualifier);
            return;
        }
        try {
            channel.remove(getExecutionId());
            logger.info("Removed execution {} from executor {}.", getExecutionId(), executorQualifier);
        } catch (Exception ex) {
            logger.warn("Error while interrupting execution {} on executor: {}",
                getExecutionId(),
                comp.getExecutorHandle().getContactInfo(),
                ex);
        }
    }

    private void forEachExecutor(BiConsumer<ICompositionGraph, ExecutorCommChannel> cb) {
        List<ICompositionGraph> compositionGraphs = choreography.getCompositionGraph();
        for (ICompositionGraph comp : compositionGraphs) {
            ExecutorCommChannel executorCommChannel = channelService.interExecutorCommChannel(comp.getExecutorHandle().getContactInfo());
            cb.accept(comp, executorCommChannel);
        }
    }
}
