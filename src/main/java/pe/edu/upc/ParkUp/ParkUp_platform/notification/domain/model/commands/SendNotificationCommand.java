package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands;

import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationChannel;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationType;

/**
 * Command to send a notification
 *
 * @param userId   The ID of the user to notify
 * @param channel  The notification channel
 * @param type     The notification type
 * @param title    The notification title
 * @param message  The notification message
 * @param metadata Additional metadata as JSON string
 */
public record SendNotificationCommand(
        Long userId,
        NotificationChannel channel,
        NotificationType type,
        String title,
        String message,
        String metadata
) {
}
