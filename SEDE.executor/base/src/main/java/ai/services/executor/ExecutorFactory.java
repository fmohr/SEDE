package ai.services.executor;

import ai.services.channels.CachingChannelService;
import ai.services.channels.ChannelService;
import ai.services.channels.StdLocalChannelService;
import ai.services.execution.local.LocalWorkers;
import ai.services.execution.operator.ServiceInstanceFactory;
import ai.services.execution.operator.TaskOperator;
import ai.services.execution.operator.local.StdLocalOperations;
import ai.services.executor.local.LocalExecutorRegistry;
import de.upb.sede.exec.ExecutorConfiguration;
import de.upb.sede.exec.IExecutorConfiguration;
import de.upb.sede.exec.MutableExecutorConfiguration;
import de.upb.sede.util.DeepImmutableCopier;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorFactory {

    private IExecutorConfiguration configuration;

    private ExecutorConfiguration.Builder configBuilder = ExecutorConfiguration.builder();

    private ChannelService channelService;

    private ExecutorService executorService;

    private LocalExecutorRegistry executorRegistry = LocalExecutorRegistry.INSTANCE;

    private AccessControlQueue accessControlQueue;

    private boolean ownedExecutorService = false;

    private Executor executor;

    private LocalWorkers localWorkers;

    private ExecutorRegistrationController executorRegistrationController;

    private boolean registerOnBuild = true;

    private boolean startWorkersOnBuild = true;

    public ExecutorFactory() {

    }

    public ExecutorConfiguration.Builder getConfigBuilder() {
        return configBuilder;
    }

    public void setConfiguration(IExecutorConfiguration configuration) {
        this.configuration = configuration;
    }

    private IExecutorConfiguration getOrCreateConfiguration() {
        configuration = Objects.requireNonNullElseGet(configuration, () -> configBuilder.build());
        setExecutorIdIfMissing();
        return this.configuration;
    }

    private void setExecutorIdIfMissing() {
        if(configuration.getExecutorId() == null) {
            configuration = ExecutorConfiguration.builder()
                .from(configuration)
                .executorId(UUID.randomUUID().toString())
                .build();
        }
    }

    private ChannelService getOrCreateChannelService() {
        if(channelService == null) {
            channelService = new CachingChannelService(
                new StdLocalChannelService(
                    executorRegistry, getOrCreateConfiguration().getServiceStoreLocation()));
        }
        return channelService;
    }

    private ExecutorService getOrCreateExecutorService() {
        if(executorService == null) {
            ownedExecutorService = true;
            executorService = Executors.newCachedThreadPool(); // The number of threads is bounded by LocalWorkers
        }
        return executorService;
    }

    private AccessControlQueue getOrCreateACQ() {
        if(accessControlQueue != null) {
            return accessControlQueue;
        }
        accessControlQueue = new AccessControlQueue();
        return accessControlQueue;
    }

    private ExecutorRegistrationController getOrCreateRegistCtrl() {
        if(executorRegistrationController != null) {
            return executorRegistrationController;
        }
        executorRegistrationController = new GatewayRESTRegistration(executorRegistry);
        return executorRegistrationController;
    }

    public Executor build() {
        if(executor != null) {
            return executor;
        }
        setExecutorIdIfMissing();
        executor = new Executor(getOrCreateACQ(),
            getOrCreateConfiguration());
        if(startWorkersOnBuild) {
            startWorkers();
        }
        if(registerOnBuild) {
            registerOnBuild();
        }
        return executor;
    }

    private void startWorkers() {
        ChannelService channelService = getOrCreateChannelService();
        ServiceInstanceFactory serviceInstanceFactory = new ServiceInstanceFactory(getOrCreateConfiguration().getExecutorId());
        StdLocalOperations localOperations = new StdLocalOperations(serviceInstanceFactory, channelService);
        TaskOperator taskOperator = localOperations.allStdLocalOperators();
        this.localWorkers = new LocalWorkers(getOrCreateACQ(), taskOperator, getOrCreateExecutorService());
        localWorkers.setDesiredThreadCount(getOrCreateConfiguration().getThreadNumber());
        if(ownedExecutorService) {
            executor.addShutdownHook(() -> {
                localWorkers.shutdown();
            });
        }

    }

    private void registerOnBuild() {
        ExecutorRegistrationController registrationController = getOrCreateRegistCtrl();
        registrationController.register(executor);
    }
}
