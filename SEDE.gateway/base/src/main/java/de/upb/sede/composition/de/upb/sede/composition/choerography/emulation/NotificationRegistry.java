package de.upb.sede.composition.de.upb.sede.composition.choerography.emulation;

import de.upb.sede.composition.graphs.nodes.INotification;
import de.upb.sede.composition.graphs.nodes.Notification;

import java.util.concurrent.atomic.AtomicLong;

public class NotificationRegistry {

    private AtomicLong nextNotificationId = new AtomicLong(1);

    public NotificationRegistry() {

    }

    INotification createNotification(String description) {
        long id = nextNotificationId.getAndIncrement();
        String notificationQualifier = String.valueOf("n" + id);
        return Notification.builder()
            .qualifier(notificationQualifier)
            .description(description)
            .build();
    }

}
