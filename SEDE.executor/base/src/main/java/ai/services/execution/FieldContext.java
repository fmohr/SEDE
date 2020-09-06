package ai.services.execution;

import de.upb.sede.composition.graphs.nodes.INotification;
import de.upb.sede.core.SEDEObject;

/**
 * Each execution has a single scope of fields that map fieldnames to service instances and data values.
 * This scope is defined by field context implementations.
 */
public interface FieldContext {

    String contextIdentifier();

    boolean hasField(String fieldname);

    void setFieldValue(String fieldname, SEDEObject value);

    SEDEObject getFieldValue(String fieldname);

    void deleteField(String fieldname);

    void pushNotification(INotification ntf);

    boolean hasNotification(INotification ntf);

}
