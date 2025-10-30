package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.queries;

/**
 * Query to get all notifications for a user
 *
 * @param userId The user ID
 */
public record GetNotificationsByUserIdQuery(Long userId) {
}
