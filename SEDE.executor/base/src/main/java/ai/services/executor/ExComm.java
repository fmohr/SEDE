package ai.services.executor;

import de.upb.sede.composition.graphs.nodes.INotification;
import de.upb.sede.core.SEDEObject;

public interface ExComm {

    void interrupt();

    void transmit(String fieldname, SEDEObject value);

    boolean isByteChannel();

    void notify(INotification notification);

}
