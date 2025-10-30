package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands;

/**
 * Command to deactivate a user device
 *
 * @param deviceId The device ID
 * @param userId   The user ID (for authorization)
 */
public record DeactivateDeviceCommand(Long deviceId, Long userId) {
}
