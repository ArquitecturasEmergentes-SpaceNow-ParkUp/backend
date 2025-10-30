package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.queries;

/**
 * Query to get active devices for a user
 *
 * @param userId The user ID
 */
public record GetActiveDevicesByUserIdQuery(Long userId) {
}
