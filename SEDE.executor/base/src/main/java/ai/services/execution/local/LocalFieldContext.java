package ai.services.execution.local;

import ai.services.execution.FieldContext;
import ai.services.composition.graphs.nodes.INotification;
import ai.services.core.SEDEObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LocalFieldContext implements FieldContext {

    private final String contextIdentifier;

    private final Map<String, SEDEObject> fields = new HashMap<>();

    private final Set<INotification> ntfPool = new HashSet<>();

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
        this.ntfPool.add(ntf);
    }

    @Override
    public synchronized boolean hasNotification(INotification ntf) {
        return ntfPool.contains(ntf);
    }
}
