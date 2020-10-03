package ai.services.channels.local;

import ai.services.channels.DownloadLink;
import ai.services.channels.DataChannel;
import ai.services.channels.InProcessDataChannel;
import ai.services.channels.UploadLink;
import ai.services.core.SEDEObject;
import ai.services.core.SemanticDataField;
import ai.services.execution.GraphTaskExecution;
import ai.services.executor.AccessControlQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Optional;

class InProcessHandOffDataChannel implements DataChannel, InProcessDataChannel {

    private static final Logger logger = LoggerFactory.getLogger(InProcessHandOffDataChannel.class);

    private final AccessControlQueue acq;

    private final String executionId;

    public InProcessHandOffDataChannel(AccessControlQueue acq, String executionId) {
        this.acq = acq;
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
                        logger.warn("This shouldn't have happened.", e);
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
                setObject(fieldname, semanticDataField);
            }
        };
    }

    @Override
    public DownloadLink getDownloadLink(String fieldname) {
        return new DownloadLink() {
            @Override
            public InputStream getStream() {
                byte[] bytes = getBytes();
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public byte[] getBytes() {
                SEDEObject fieldValue = getObject(fieldname);
                if (!fieldValue.isSemantic()) {
                    throw new IllegalStateException(String.format("Field %s is not in semantic form.", fieldname));
                }
                return fieldValue.getDataField();
            }

            @Override
            public void close() {

            }
        };
    }

    @Override
    public SEDEObject getObject(String fieldname) {
        Optional<GraphTaskExecution> graphTaskExecution = acq.get(executionId);
        if (!graphTaskExecution.isPresent()) {
            throw new IllegalStateException("No such execution found: " + executionId);
        }
        boolean fieldPresent = graphTaskExecution.get().hasField(fieldname);
        if (!fieldPresent) {
            throw new IllegalStateException(String.format("Field %s is not present.", fieldname));
        }
        return graphTaskExecution.get().getFieldValue(fieldname);
    }

    @Override
    public void setObject(String fieldname, SEDEObject fieldValue) {
        boolean eFound = acq.computeIfPresent(executionId, execution -> execution.setFieldValue(fieldname, fieldValue));
        if (!eFound) {
            throw new IllegalStateException("No such execution found: " + executionId);
        }
    }
}
