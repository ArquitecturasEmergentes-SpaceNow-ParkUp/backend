package pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.aggregates.NotificationLog;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.resources.NotificationResource;

/**
 * Assembler to convert NotificationLog entity to NotificationResource
 */
public class NotificationResourceFromEntityAssembler {

    /**
     * Converts a NotificationLog entity to a NotificationResource
     *
     * @param entity The NotificationLog entity
     * @return The NotificationResource
     */
    public static NotificationResource toResourceFromEntity(NotificationLog entity) {
        return new NotificationResource(
                entity.getId(),
                entity.getUserId(),
                entity.getChannel().name(),
                entity.getType().name(),
                entity.getStatus().name(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getMetadata(),
                entity.getSentAt(),
                entity.getReadAt(),
                entity.getCreatedAt()
        );
    }
}
