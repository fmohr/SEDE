package ai.services;

import ai.services.channels.*;
import ai.services.composition.DeployRequest;
import ai.services.composition.graphs.nodes.ICompositionGraph;
import ai.services.core.SEDEObject;
import ai.services.core.SemanticDataField;
import ai.services.exec.ExecutionState;
import ai.services.exec.IExecutionError;
import ai.services.exec.IExecutorContactInfo;
import ai.services.exec.IExecutionState;
import ai.services.requests.resolve.beta.IChoreography;
import ai.services.requests.resolve.beta.IResolveRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExecutionController implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionController.class);

    private final ChannelService channelService;

    private final IResolveRequest resolveRequest;

    private final IChoreography choreography;

    private String executionId;

    private final Map<String, IExecutionState> executionStates = new HashMap<>();

    private final Set<String> injectedFields = Collections.synchronizedSet(new HashSet<>());

    private final Set<String> retrievedFields = Collections.synchronizedSet(new HashSet<>());

    private final List<IExecutionError> executionErrors = Collections.synchronizedList(new ArrayList<>());

    private final AtomicBoolean startedFlag = new AtomicBoolean(false);

    private final AtomicBoolean finishedFlag = new AtomicBoolean(false);

    private final AtomicBoolean removedFlag = new AtomicBoolean(false);

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

    public IResolveRequest getResolveRequest() {
        return resolveRequest;
    }

    public IChoreography getChoreography() {
        return choreography;
    }

    public synchronized void startExecution() {
        if(removedFlag.get()) {
            throw new IllegalStateException(String.format("Execution '%s' is closed.", getExecutionId()));
        }
        if(startedFlag.get()) {
            throw new IllegalStateException(String.format("Execution '%s' is already started.", getExecutionId()));
        }
        startedFlag.set(true);
        testConnectivity();
        // TODO test intra executor connectivity
        deploy();
        start();
    }

    private void testConnectivity() {
        Map<IExecutorContactInfo, Optional<Long>> connectivityMap = new HashMap<>();
        AtomicBoolean connectionFailed = new AtomicBoolean(false);
        callForEachExecutor((comp, channel) -> {
            Optional<Long> rtt = channel.testConnectivity();
            connectivityMap.put(comp.getExecutorHandle().getContactInfo(), rtt);
            if (rtt.isPresent()) {
                logger.debug("Connectivity check was successful: rtt  is {} ms to {}", rtt.get(), comp.getExecutorHandle().getQualifier());
            } else {
                connectionFailed.set(true);
                logger.warn("Connectivity check was NOT successful to {}. \n Contact info: {}", comp.getExecutorHandle().getContactInfo().getQualifier(),
                    comp.getExecutorHandle().getContactInfo());
            }
        });
        if(connectionFailed.get()) {
            throw new IllegalStateException("Connectivity check unsuccessful. Couldn't connect to the following executors: \n\t" +
                connectivityMap.entrySet().stream().filter(entry -> !entry.getValue().isPresent())
                    .map(entry -> entry.getKey().getQualifier()).collect(Collectors.joining("\t\n"))
            );
        }
    }

    private void deploy() {
        callForEachExecutor((comp, channel) -> {
            try {
                setExecutionState(comp, this::setDeployed);
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

    private IExecutionState setDeployed(IExecutionState oldState) {
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
        callForEachExecutor((comp, channel) -> {
            try {
                setExecutionState(comp, this::setStarted);
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

    private IExecutionState setStarted(IExecutionState oldState) {
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
        if(!choreography.getInitialFieldLocation().containsKey(fieldname)) {
            throw new IllegalArgumentException("The given field is not an initial field: " + fieldname);
        }
        Objects.requireNonNull(fieldname);
        Objects.requireNonNull(fieldValue);
        boolean added = injectedFields.add(fieldname);
        if(!added) {
            throw new IllegalArgumentException("The given field has already been injected: " + fieldname);
        }
        IExecutorContactInfo fieldDestination = choreography.getInitialFieldLocation().get(fieldname);
        DataChannel dataChannel = channelService.interExecutorCommChannel(fieldDestination)
            .dataChannel(getExecutionId());
        if(dataChannel instanceof InProcessDataChannel) {
            ((InProcessDataChannel) dataChannel).setObject(fieldname, fieldValue);
        } else {
            if(!fieldValue.isSemantic()) {
                throw new IllegalArgumentException("The given initial field is not semantic. Marshal the value before injecting it: " + fieldname + "." +
                    " Given field type: " + fieldValue.getType());
            }
            SemanticDataField semanticField = (SemanticDataField) fieldValue;
            try(UploadLink uploadLink = dataChannel.getUploadLink(fieldname, semanticField.getType())) {
                uploadLink.setPayload(semanticField.getDataField());
            } catch (Exception e) {
                throw new RuntimeException("Error uploading field: " + fieldname + " to executor: " + fieldDestination);
            }
        }
    }

    private void assertAllInputFieldsProvided() {
        if(!injectedFields.containsAll(choreography.getInitialFieldLocation().keySet())) {
            throw new IllegalStateException("The following initial fields have not been injected yet:\n\t" +
                choreography.getInitialFieldLocation().keySet().stream().filter(f -> !injectedFields.contains(f)).collect(Collectors.joining(", ")));
        }
    }

    public synchronized void waitUntilFinished() throws InterruptedException, ExecutionRuntimeException, ExecutionErrorException {
        assertStarted();
        assertAllInputFieldsProvided();
        try {
            callForEachExecutorWithException((comp, channel) -> {
                boolean isFinished = checkState(comp, state -> {
                    if (state.isPresent() && state.get().isStarted() && state.get().isDeployed()) {
                        return state.get().isFinished();
                    } else {
                        logger.warn("Executor state should have been set to started and deployed.");
                        throw new IllegalStateException("Buggy executor state");
                    }
                });
                if(isFinished) {
                    logger.info("Execution is already marked as finished: {}", comp.getExecutorHandle().getQualifier());
                    return;
                }
                joinExecution(comp, channel);
                collectErrors(comp, channel);
            });
        } catch(InterruptedException ex) {
            logger.info("Interrupted while waiting on execution {}", getExecutionId(), ex);
            throw new InterruptedException();
        } catch (Exception ex) {
            logger.warn("Error while waiting for execution to finish: {}", ex.getMessage());
            throw new ExecutionRuntimeException(ex);
        }
        finishedFlag.set(true);
        if(!executionErrors.isEmpty()) {
            throw new ExecutionErrorException(getExecutionId(), executionErrors);
        }
    }

    private void joinExecution(ICompositionGraph comp, ExecutorCommChannel channel) throws InterruptedException {
        channel.joinExecution(getExecutionId());
        setExecutionState(comp, this::setFinished);
    }

    private IExecutionState setFinished(IExecutionState oldState) {
        ExecutionState.Builder builder = ExecutionState.builder();
        if(oldState != null) {
            if(oldState.isFinished()) {
                throw new IllegalStateException("Execution already finished on " + executionId);
            }
            builder.from(oldState);
        }
        builder.isFinished(true);
        return builder.build();
    }

    private void assertFinished() {
        if(!finishedFlag.get()) {
            throw new IllegalStateException("Not finished: " + getExecutionId());
        }
    }

    private void collectErrors(ICompositionGraph comp, ExecutorCommChannel channel) {
        List<IExecutionError> errors = channel.getErrors(getExecutionId());
        if(!errors.isEmpty()) {
            logger.warn("Execution {} has errored.", comp.getExecutorHandle().getQualifier());
            this.executionErrors.addAll(errors);
        }
    }

    private void assertNotErrored() {
        if(!executionErrors.isEmpty()) {
            throw new IllegalStateException("Execution has failed: " + getExecutionId());
        }
    }

    public SEDEObject getReturnValue(String fieldname) {
        assertFinished();
        assertNotErrored();
        if (!choreography.getReturnFieldLocation().containsKey(fieldname)) {
            throw new IllegalArgumentException("The given field is not a return field: " + fieldname);
        }
        Objects.requireNonNull(fieldname);
        boolean added = retrievedFields.add(fieldname);
        if(!added) {
            throw new IllegalArgumentException("The field has already been retrieved: " + fieldname);
        }
        IExecutorContactInfo fieldOwner = choreography.getReturnFieldLocation().get(fieldname);
        DataChannel dataChannel = channelService
            .interExecutorCommChannel(fieldOwner)
            .dataChannel(getExecutionId());
        if(dataChannel instanceof InProcessDataChannel) {
            InProcessDataChannel inProcessDataChannel = (InProcessDataChannel) dataChannel;
            return inProcessDataChannel.getObject(fieldname);
        }
        try(DownloadLink downloadLink = dataChannel.getDownloadLink(fieldname)) {
            byte[] data = downloadLink.getBytes();
            return new SemanticDataField("UnknownSemanticType", data);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching field "
                + fieldname + " from executor with contact info: "
                +  fieldOwner,
                e);
        }
    }

    @Override
    public synchronized void close()  {
        removedFlag.set(true);
        interrupt();
        remove();
    }

    public synchronized void interrupt() {
        assertStarted();
        if(!finishedFlag.get()) {
            callForEachExecutor(this::interrupt);
        }
    }

    private void interrupt(ICompositionGraph comp, ExecutorCommChannel channel) {
        String qualifier = comp.getExecutorHandle().getQualifier();
        final boolean interruptNeeded = checkState(qualifier, state -> state.isPresent()
            && state.get().isStarted()
            && !state.get().isFinished());
        if(!interruptNeeded) {
            logger.info("Didn't interrupt execution {} on executor {}: Current state: {}", getExecutionId(), qualifier, executionStates.get(qualifier));
            return;
        }
        try {
            channel.interrupt(getExecutionId());
            logger.info("Interrupted execution {} on executor {}.", getExecutionId(), qualifier);
        } catch (Exception ex) {
            logger.warn("Error while interrupting execution {} on executor: {}",
                getExecutionId(),
                comp.getExecutorHandle().getContactInfo(),
                ex);
        }
    }

    private void remove() {
        callForEachExecutor(this::remove);
    }

    private void remove(ICompositionGraph comp, ExecutorCommChannel channel) {
        String executorQualifier = comp.getExecutorHandle().getQualifier();
        boolean isDeployed = checkState(executorQualifier, state -> state.isPresent() &&  state.get().isDeployed());
        if(!isDeployed) {
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

//    private boolean checkState(String executorQualifier, Function<IExecutionState, Boolean> stateCheck) {
//        return this.checkState(executorQualifier, stateCheck, true);
//    }

    private void setExecutionState(ICompositionGraph graph, Function<IExecutionState, IExecutionState> remapper) {
        this.setExecutionState(graph.getExecutorHandle().getQualifier(), remapper);
    }

    private void setExecutionState(String executorQualifier, Function<IExecutionState, IExecutionState> remapper) {
        this.executionStates.compute(executorQualifier, (q, beforeState) -> remapper.apply(beforeState));
    }

    private boolean checkState(ICompositionGraph graph, Function<Optional<IExecutionState>, Boolean> stateCheck) {
        return this.checkState(graph.getExecutorHandle().getQualifier(), stateCheck);
    }

    private boolean checkState(String executorQualifier, Function<Optional<IExecutionState>, Boolean> stateCheck) {
        IExecutionState state = this.executionStates.get(executorQualifier);
        return stateCheck.apply(Optional.ofNullable(state));
    }


    private void callForEachExecutor(ExecutorCallbackQuiet cb) {
        List<ICompositionGraph> compositionGraphs = choreography.getCompositionGraph();
        for (ICompositionGraph comp : compositionGraphs) {
            ExecutorCommChannel executorCommChannel = channelService.interExecutorCommChannel(comp.getExecutorHandle().getContactInfo());
            cb.runCallback(comp, executorCommChannel);
        }
    }
    private void callForEachExecutorWithException(ExecutorCallbackWithException cb) throws Exception {
        List<ICompositionGraph> compositionGraphs = choreography.getCompositionGraph();
        for (ICompositionGraph comp : compositionGraphs) {
            ExecutorCommChannel executorCommChannel = channelService.interExecutorCommChannel(comp.getExecutorHandle().getContactInfo());
            cb.runCallback(comp, executorCommChannel);
        }
    }
    private interface ExecutorCallbackQuiet {
        void runCallback(ICompositionGraph comp, ExecutorCommChannel channel);
    }
    private interface ExecutorCallbackWithException {
        void runCallback(ICompositionGraph comp, ExecutorCommChannel channel) throws Exception;
    }

}
