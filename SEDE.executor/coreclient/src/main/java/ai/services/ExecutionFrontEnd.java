package ai.services;

import ai.services.channels.ChannelService;
import ai.services.channels.ExecutionDataChannel;
import ai.services.channels.ExecutorCommChannel;
import ai.services.channels.GraphDeploymentException;
import ai.services.composition.DeployRequest;
import ai.services.composition.graphs.nodes.ICompositionGraph;
import ai.services.requests.resolve.beta.IChoreography;
import ai.services.requests.resolve.beta.IResolveRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class ExecutionFrontEnd implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionFrontEnd.class);

    private final ChannelService channelService;

    private final IResolveRequest resolveRequest;

    private final IChoreography choreography;

    private String executionId;

    private AtomicBoolean closeFlag = new AtomicBoolean(false);

    private AtomicBoolean deployFlag = new AtomicBoolean(false);

    private AtomicBoolean startFlag = new AtomicBoolean(false);

    public ExecutionFrontEnd(ChannelService channelService, IResolveRequest resolveRequest, IChoreography choreography) {
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

    public synchronized void execute() {
        if(closeFlag.get()) {
            throw new IllegalStateException(String.format("Execution '%s' is closed.", getExecutionId()));
        }
        deploy();
        // testConnectivity();
        start();
    }

    private void deploy() {
        List<ICompositionGraph> compositionGraphs = choreography.getCompositionGraph();
        deployFlag.set(true);
        for (ICompositionGraph comp : compositionGraphs) {
            try {
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
            }
        }
    }

    private void start() {

    }


    @Override
    public synchronized void close()  {
        closeFlag.set(true);
        if(startFlag.get()) {
            interrupt();
        }
        if(deployFlag.get()) {
            remove();
        }

    }

    public synchronized void interrupt() {
        forEachExecutor((comp, channel) -> {
            try {
                channel.interrupt(getExecutionId());
            } catch (Exception ex) {
                logger.warn("Error while interrupting execution {} on executor: {}",
                    getExecutionId(),
                    comp.getExecutorHandle().getContactInfo(),
                    ex);
            }
        });
    }

    private synchronized void remove() {
        forEachExecutor((comp, channel) -> {
            try {
                channel.remove(getExecutionId());
            } catch (Exception ex) {
                logger.warn("Error while interrupting execution {} on executor: {}",
                    getExecutionId(),
                    comp.getExecutorHandle().getContactInfo(),
                    ex);
            }
        });
    }

    private void forEachExecutor(BiConsumer<ICompositionGraph, ExecutorCommChannel> cb) {
        List<ICompositionGraph> compositionGraphs = choreography.getCompositionGraph();
        for (ICompositionGraph comp : compositionGraphs) {
            ExecutorCommChannel executorCommChannel = channelService.interExecutorCommChannel(comp.getExecutorHandle().getContactInfo());
            cb.accept(comp, executorCommChannel);
        }
    }
}
