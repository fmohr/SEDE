package ai.services.composition.choerography.emulation;

import ai.services.composition.graphs.nodes.Notification;
import ai.services.composition.IndexFactory;
import ai.services.composition.graphs.nodes.INotification;

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
