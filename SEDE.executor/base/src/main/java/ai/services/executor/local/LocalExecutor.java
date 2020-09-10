package ai.services.executor.local;

import ai.services.channels.ChannelService;
import ai.services.channels.StdChannelService;
import ai.services.execution.local.LocalWorkers;
import ai.services.execution.operator.ServiceInstanceFactory;
import ai.services.execution.operator.TaskOperator;
import ai.services.execution.operator.local.StdLocalOperations;
import ai.services.executor.AccessControlQueue;
import ai.services.executor.Executor;
import de.upb.sede.exec.IExecutorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalExecutor extends Executor {

    private static final Logger logger = LoggerFactory.getLogger(LocalExecutor.class);


    private LocalWorkers localWorkers;

    private ChannelService channelService;

    private boolean ownedExecutorService = false;

    private ExecutorService executorService;

    private boolean started = false;

    public LocalExecutor(AccessControlQueue acq, IExecutorConfiguration configuration) {
        super(acq, configuration);
    }


    private synchronized ChannelService getOrCreateChannelService() {
        if(channelService == null) {
            channelService = new StdChannelService(getConfiguration().getServiceStoreLocation());
        }
        return channelService;
    }

    private synchronized ExecutorService getOrCreateExecutorService() {
        if(executorService == null) {
            ownedExecutorService = true;
            executorService = Executors.newCachedThreadPool(); // The number of threads is bounded by LocalWorkers
        }
        return executorService;
    }


    public synchronized void start() {
        if(started) {
            throw new IllegalStateException("Executor has already started.");
        }
        ChannelService channelService = getOrCreateChannelService();
        ServiceInstanceFactory serviceInstanceFactory = new ServiceInstanceFactory(getConfiguration().getExecutorId());
        StdLocalOperations localOperations = new StdLocalOperations(serviceInstanceFactory, channelService);
        TaskOperator taskOperator = localOperations.allStdLocalOperators();
        this.localWorkers = new LocalWorkers(acq(), taskOperator, getOrCreateExecutorService());
        localWorkers.setDesiredThreadCount(getConfiguration().getThreadNumber());
        started = true;
    }

    public synchronized void pause() {
        localWorkers.setDesiredThreadCount(0);
    }

    public synchronized void continueOperation() {
        localWorkers.setDesiredThreadCount(getConfiguration().getThreadNumber());
    }

    public synchronized void shutdown() {
        super.shutdown();
        if(ownedExecutorService)
            localWorkers.shutdown();
    }

}
