package ai.services.execution.local;

import ai.services.composition.INtfInstance;
import ai.services.composition.NtfInstance;
import ai.services.execution.FieldContext;
import ai.services.composition.graphs.nodes.INotification;
import ai.services.core.SEDEObject;

import java.util.*;

public class LocalFieldContext implements FieldContext {

    private final String contextIdentifier;

    private final Map<String, SEDEObject> fields = new HashMap<>();

    private final Map<String, INtfInstance> ntfPool = new HashMap<>();

    public LocalFieldContext(String contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    @Override
    public String contextIdentifier() {
        return contextIdentifier;
    }

    public synchronized boolean hasField(String fieldname) {
        return fields.containsKey(fieldname);
    }

    public synchronized void setFieldValue(String fieldname, SEDEObject value) {
        this.fields.put(fieldname, value);
    }

    public synchronized SEDEObject getFieldValue(String fieldname) {
        if(fields.containsKey(fieldname))
            return fields.get(fieldname);
        else
            throw new IllegalStateException("field is unassigned: " + fieldname);
    }

    public synchronized void deleteField(String fieldname) {
        if(fields.containsKey(fieldname))
            fields.remove(fieldname);
        else
            throw new IllegalStateException("field is unassigned: " + fieldname);
    }

    @Override
    public synchronized void pushNotification(INotification ntf) {
        final INtfInstance ntfInstance;
        if(ntf instanceof INtfInstance) {
            ntfInstance = (INtfInstance) ntf;
        } else {
            ntfInstance = NtfInstance.builder()
                .from(ntf)
                .isSuccessfulNotification(true)
                .executionId(contextIdentifier())
                .build();
        }
        this.ntfPool.put(ntf.getQualifier(), ntfInstance);
    }

    @Override
    public synchronized boolean hasNotification(INotification notification) {
        return ntfPool.containsKey(notification.getQualifier());
    }

    @Override
    public synchronized Optional<INtfInstance> getNotification(INotification ntf) {
        return Optional.ofNullable(ntfPool.get(ntf.getQualifier()));
    }

}
