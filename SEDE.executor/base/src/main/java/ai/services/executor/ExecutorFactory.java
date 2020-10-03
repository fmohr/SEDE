package ai.services.executor;

import ai.services.channels.CachingChannelService;
import ai.services.channels.ChannelService;
import ai.services.channels.StdLocalChannelService;
import ai.services.execution.local.LocalWorkers;
import ai.services.execution.operator.ServiceInstanceFactory;
import ai.services.execution.operator.TaskOperator;
import ai.services.execution.operator.local.StdLocalOperations;
import ai.services.executor.local.LocalExecutorInstanceRegistrationCtrl;
import ai.services.executor.local.LocalExecutorInstanceRegistry;
import ai.services.exec.ExecutorConfiguration;
import ai.services.exec.IExecutorConfiguration;
import ai.services.interfaces.ExecutorRegistrant;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorFactory {

    private IExecutorConfiguration configuration;

    private final ExecutorConfiguration.Builder configBuilder = ExecutorConfiguration.builder();

    private ChannelService channelService;

    private ExecutorService executorService;

    private LocalExecutorInstanceRegistry executorInstanceRegistry = LocalExecutorInstanceRegistry.INSTANCE;

    private ExecutorRegistrant gatewayExecutorRegistrant = null;

    private AccessControlQueue accessControlQueue;

    private boolean ownedExecutorService = false;

    private Executor executor;

    private LocalWorkers localWorkers;

    private ExecutorInstanceRegistrationController executorRegistrationController;

    private boolean registerOnBuild = true;

    private boolean startWorkersOnBuild = true;

    public ExecutorFactory() {

    }

    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setExecutorInstanceRegistry(LocalExecutorInstanceRegistry executorInstanceRegistry) {
        this.executorInstanceRegistry = executorInstanceRegistry;
    }

    public void setExecutorRegistrationController(ExecutorInstanceRegistrationController executorRegistrationController) {
        this.executorRegistrationController = executorRegistrationController;
    }

    public void registerToLocalGateway(ExecutorRegistrant registrant) {
        this.gatewayExecutorRegistrant = registrant;
    }

    public void setRegisterOnBuild(boolean registerOnBuild) {
        this.registerOnBuild = registerOnBuild;
    }

    public void noInstanceRegistration() {
        executorInstanceRegistry = null;
    }

    public void setStartWorkersOnBuild(boolean startWorkersOnBuild) {
        this.startWorkersOnBuild = startWorkersOnBuild;
    }

    public ExecutorConfiguration.Builder getConfigBuilder() {
        return configBuilder;
    }

    public void setConfiguration(IExecutorConfiguration configuration) {
        this.configuration = configuration;
    }

    private IExecutorConfiguration getOrCreateConfiguration() {
        configuration = Objects.requireNonNullElseGet(configuration, configBuilder::build);
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
                    executorInstanceRegistry,
                    getOrCreateConfiguration().getServiceStoreLocation()));
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

    private ExecutorInstanceRegistrationController getOrCreateRegistCtrl() {
        if(executorRegistrationController != null) {
            return executorRegistrationController;
        }
        executorRegistrationController = ExecutorInstanceRegistrationController.chain(
            new GatewayRESTRegistration(),
            new GatewayLocalRegistration(gatewayExecutorRegistrant),
            new LocalExecutorInstanceRegistrationCtrl(executorInstanceRegistry)
        );
        return executorRegistrationController;
    }

    public Executor build() {
        if(executor != null) {
            return executor;
        }
        executor = new Executor(getOrCreateACQ(),
            getOrCreateConfiguration());
        if(startWorkersOnBuild) {
            startWorkers();
        }
        if(registerOnBuild) {
            registerExecutor();
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
            executor.addShutdownHook(() -> localWorkers.shutdown());
        } else {
            executor.addShutdownHook(() -> localWorkers.setDesiredThreadCount(0));
        }

    }

    private void registerExecutor() {
        ExecutorInstanceRegistrationController registrationController = getOrCreateRegistCtrl();
        registrationController.register(executor);
    }

    public ChannelService buildChannel() {
        build();
        return getOrCreateChannelService();
    }
}
