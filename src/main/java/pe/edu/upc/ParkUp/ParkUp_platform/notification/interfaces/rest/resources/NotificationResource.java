package pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Resource representing a notification in API responses
 *
 * @param id         The notification ID
 * @param userId     The user ID
 * @param channel    The notification channel
 * @param type       The notification type
 * @param status     The notification status
 * @param title      The notification title
 * @param message    The notification message
 * @param metadata   Additional metadata
 * @param sentAt     When the notification was sent
 * @param readAt     When the notification was read
 * @param createdAt  Creation timestamp
 */
public record NotificationResource(
        Long id,
        Long userId,
        String channel,
        String type,
        String status,
        String title,
        String message,
        String metadata,
        LocalDateTime sentAt,
        LocalDateTime readAt,
        java.util.Date createdAt
) {
}
