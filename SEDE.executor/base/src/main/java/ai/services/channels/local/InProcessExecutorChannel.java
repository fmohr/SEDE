package ai.services.channels.local;

import ai.services.channels.*;
import ai.services.execution.GraphTaskExecution;
import ai.services.executor.Executor;
import ai.services.composition.graphs.nodes.ICompositionGraph;
import ai.services.composition.graphs.nodes.INotification;
import ai.services.core.SemanticDataField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
            logInterruptError(executionId, "No such execution found.");
        }
    }

    private void logInterruptError(String executionId, String errorMessage) {
        logger.warn("Interrupting execution with id '{}', on executor with contact info '{}', was not successful:\n {}",
            executionId, getExecutor().contactInfo(), errorMessage);
    }

    @Override
    public void pushNotification(String executionId, INotification notification) throws PushNotificationException {
        try {
            boolean exFound = getExecutor().acq().computeIfPresent(executionId, execution -> {
                execution.pushNotification(notification);
            });
            if(!exFound) {
                throw new RuntimeException("No such execution");
            }
        } catch (RuntimeException e) {
            throw new PushNotificationException(String.format("Error pushing notification %s to execution %s on executor %s: %s",
                notification, executionId, getExecutor().contactInfo(), e.getMessage()));
        }
    }

    @Override
    public ExecutionDataChannel dataChannel(String executionId) {
        final Executor executor = getExecutor();
        return new HandOffDataChannel(executionId);
    }

    @Override
    public void deployGraph(String executionId, ICompositionGraph toBeDeployed) throws GraphDeploymentException {
        final Executor executor = getExecutor();
        GraphTaskExecution graphTaskExecution;
        try {
            graphTaskExecution = executor.acq().create(executionId);
        } catch(IllegalStateException ex) {
            throw new GraphDeploymentException(ex);
        }
        executor.acq().compute(graphTaskExecution, execution -> {
            execution.addGraph(toBeDeployed);
            execution.startExecution();
        });
    }

//    class LocalExecutorNotRegisteredException extends Exception {
//        LocalExecutorNotRegisteredException() {
//            super("No local executor is registered with contact info: " + contactInfo
//                + "\n Set of registered executors: \n\t" +
//                (registry.isEmpty() ? "None registered" : String.join("\n\t ", registry.keySet())));
//        }
//    }

    private class HandOffDataChannel implements ExecutionDataChannel {

        private final String executionId;

        public HandOffDataChannel(String executionId) {
            this.executionId = executionId;
        }

        @Override
        public UploadLink getUploadLink(String fieldname, String semantictype) {
            return new UploadLink() {
                byte[] payload = null;
                ByteArrayOutputStream payloadStream = null;

                @Override
                public OutputStream getPayloadStream() throws IOException {
                    if (payloadStream == null) {
                        payloadStream = new ByteArrayOutputStream();
                    }
                    if (payload != null) {
                        payloadStream.write(payload);
                        payload = null;
                    }
                    return payloadStream;
                }

                @Override
                public void setPayload(byte[] payload) {
                    if (payloadStream != null) {
                        try {
                            payloadStream.write(payload);
                        } catch (IOException e) {
                            logger.warn("This cannot happen.", e);
                        }
                    } else {
                        this.payload = payload;
                    }
                }

                @Override
                public void close() throws Exception {
                    if (payloadStream != null) {
                        payload = payloadStream.toByteArray();
                    }
                    if (payload == null) {
                        throw new Exception("No payload was provided.");
                    }
                    SemanticDataField semanticDataField = new SemanticDataField(semantictype, payload);
                    boolean eFound = getExecutor().acq().computeIfPresent(executionId, execution -> {
                        execution.setFieldValue(fieldname, semanticDataField);
                    });
                    if (!eFound) {
                        throw new Exception("No such execution found: " + executionId);
                    }
                }
            };
        }
    }
}
