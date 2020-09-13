package ai.services.channels;

import ai.services.execution.GraphTaskExecution;
import ai.services.executor.Executor;
import ai.services.executor.local.LocalExecutorRegistry;
import de.upb.sede.composition.graphs.nodes.ICompositionGraph;
import de.upb.sede.composition.graphs.nodes.INotification;
import de.upb.sede.core.SemanticDataField;
import de.upb.sede.exec.IExecutorContactInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

public class InProcessExecutorChannel implements ExecutorCommChannel {

    private static final Logger logger = LoggerFactory.getLogger(InProcessExecutorChannel.class);

    private final IExecutorContactInfo contactInfo;

    private final LocalExecutorRegistry registry;

    private Executor localExecutor;

    public InProcessExecutorChannel(IExecutorContactInfo contactInfo) {
        this.registry = LocalExecutorRegistry.INSTANCE;
        this.contactInfo = contactInfo;
    }

    private Executor getExecutor() throws LocalExecutorNotRegisteredException {
        if(localExecutor != null) {
            return localExecutor;
        }
        synchronized (this) {
            Executor executor = registry.get(contactInfo);
            if(executor == null) {
                throw new LocalExecutorNotRegisteredException();
            }
            this.localExecutor = executor;
            return executor;
        }
    }

    @Override
    public Optional<Long> testConnectivity() {
        try {
            getExecutor();
            return Optional.of(0L);
        } catch(LocalExecutorNotRegisteredException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void interrupt(String executionId) {
        boolean executionFound = false;
        try {
            executionFound = getExecutor().acq().computeIfPresent(executionId, GraphTaskExecution::interruptExecution);
        } catch (LocalExecutorNotRegisteredException e) {
            logInterruptError(executionId, e.getMessage());
        }
        if(!executionFound) {
            logInterruptError(executionId, "No such execution found.");
        }
    }

    private void logInterruptError(String executionId, String errorMessage) {
        logger.warn("Interrupting execution with id '{}', on executor with contact info '{}', was not successful:\n {}",
            executionId, contactInfo, errorMessage);
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
        } catch (LocalExecutorNotRegisteredException | RuntimeException e) {

            throw new PushNotificationException(String.format("Error pushing notification %s to execution %s on executor %s: %s",
                notification, executionId, contactInfo, e.getMessage()));
        }
    }

    @Override
    public ExecutionDataChannel dataChannel(String executionId) {
        try {
            final Executor executor = getExecutor();
            return (fieldname, semantictype) -> new UploadLink() {
                byte[] payload = null;
                ByteArrayOutputStream payloadStream = null;
                @Override
                public OutputStream getPayloadStream() throws IOException {
                    if(payloadStream == null) {
                        payloadStream = new ByteArrayOutputStream();
                    }
                    if(payload != null) {
                        payloadStream.write(payload);
                        payload = null;
                    }
                    return payloadStream;
                }

                @Override
                public void setPayload(byte[] payload) {
                    if(payloadStream != null) {
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
                    if(payloadStream != null) {
                        payload = payloadStream.toByteArray();
                    }
                    if(payload == null) {
                        throw new Exception("No payload was provided.");
                    }
                    SemanticDataField semanticDataField = new SemanticDataField(semantictype, payload);
                    boolean eFound = executor.acq().computeIfPresent(executionId, execution -> {
                        execution.setFieldValue(fieldname, semanticDataField);
                    });
                    if(!eFound) {
                        throw new Exception("No such execution found: " + executionId);
                    }
                }
            };
        } catch (LocalExecutorNotRegisteredException e) {
            return new ErrorDataChannel();
        }
    }

    class ErrorDataChannel implements ExecutionDataChannel {

        @Override
        public UploadLink getUploadLink(String fieldname, String semantictype) {
            return new UploadLink() {
                @Override
                public OutputStream getPayloadStream() throws IOException {
                    throw new IOException(new LocalExecutorNotRegisteredException());
                }

                @Override
                public void setPayload(byte[] payload) {

                }

                @Override
                public void close() throws Exception {
                    throw new IOException(new LocalExecutorNotRegisteredException());
                }
            };
        }
    }

    @Override
    public void deployGraph(String executionId, ICompositionGraph toBeDeployed) throws GraphDeploymentException {

    }

    class LocalExecutorNotRegisteredException extends Exception {
        LocalExecutorNotRegisteredException() {
            super("No local executor is registered with contact info: " + contactInfo
                + "\n Set of registered executors: \n\t" +
                (registry.isEmpty() ? "None registered" : String.join("\n\t ", registry.keySet())));
        }
    }

}
