package de.upb.sede.composition.choerography.emulation;

import de.upb.sede.composition.IndexFactory;
import de.upb.sede.composition.graphs.nodes.INotification;
import de.upb.sede.composition.graphs.nodes.Notification;

import java.util.concurrent.atomic.AtomicLong;

public class NotificationFactory {

    private final IndexFactory indexFactory = new IndexFactory();

    public NotificationFactory() {
        indexFactory.setOccupiedIndex(0L);
    }

    INotification createNotification(String description) {
        long id = indexFactory.create();
        String notificationQualifier = String.valueOf("n" + id);
        return Notification.builder()
            .qualifier(notificationQualifier)
            .description(description)
            .build();
    }

}
