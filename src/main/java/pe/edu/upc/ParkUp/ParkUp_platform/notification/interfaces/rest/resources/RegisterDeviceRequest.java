package pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request to register a user device
 *
 * @param userId      The user ID
 * @param deviceToken The device token from FCM/APNS
 * @param deviceType  The device type (IOS, ANDROID, WEB)
 * @param deviceName  The device name (optional)
 */
public record RegisterDeviceRequest(
        @NotNull(message = "User ID is required")
        Long userId,

        @NotBlank(message = "Device token is required")
        String deviceToken,

        String deviceType,
        String deviceName
) {
}
