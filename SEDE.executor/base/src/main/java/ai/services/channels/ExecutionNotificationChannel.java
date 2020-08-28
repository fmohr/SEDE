package ai.services.channels;

import de.upb.sede.composition.graphs.nodes.INotification;

public interface ExecutionNotificationChannel {

    void pushNotification(INotification notification);

}
