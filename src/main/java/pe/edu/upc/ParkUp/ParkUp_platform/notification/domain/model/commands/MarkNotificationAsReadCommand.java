package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands;

/**
 * Command to mark a notification as read
 *
 * @param notificationId The notification log ID
 * @param userId         The user ID (for authorization)
 */
public record MarkNotificationAsReadCommand(Long notificationId, Long userId) {
}
