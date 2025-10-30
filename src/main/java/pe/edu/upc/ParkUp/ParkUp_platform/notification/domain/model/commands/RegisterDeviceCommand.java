package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands;

/**
 * Command to register a user device for push notifications
 *
 * @param userId      The user ID
 * @param deviceToken The device token from FCM/APNS
 * @param deviceType  The device type (IOS, ANDROID, WEB)
 * @param deviceName  The device name (optional)
 */
public record RegisterDeviceCommand(
        Long userId,
        String deviceToken,
        String deviceType,
        String deviceName
) {
}
