package pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.resources;

/**
 * Resource representing a user device in API responses
 *
 * @param id          The device ID
 * @param userId      The user ID
 * @param deviceToken The device token (masked for security)
 * @param deviceType  The device type
 * @param deviceName  The device name
 * @param isActive    Whether the device is active
 * @param createdAt   Creation timestamp
 */
public record UserDeviceResource(
        Long id,
        Long userId,
        String deviceToken,
        String deviceType,
        String deviceName,
        Boolean isActive,
        java.util.Date createdAt
) {
}
