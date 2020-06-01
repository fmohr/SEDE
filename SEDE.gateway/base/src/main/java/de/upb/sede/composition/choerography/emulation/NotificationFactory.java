package de.upb.sede.composition.choerography.emulation;

import de.upb.sede.composition.graphs.nodes.INotification;
import de.upb.sede.composition.graphs.nodes.Notification;

import java.util.concurrent.atomic.AtomicLong;

public class NotificationFactory {

    private final AtomicLong nextNotificationId = new AtomicLong(1);

    public NotificationFactory() {

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
