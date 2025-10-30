package pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.resources;

/**
 * Resource for updating notification settings
 *
 * @param notificationsEnabled      Enable/disable all notifications
 * @param emailNotificationsEnabled Enable/disable email notifications
 * @param smsNotificationsEnabled   Enable/disable SMS notifications
 */
public record UpdateNotificationSettingsResource(
        Boolean notificationsEnabled,
        Boolean emailNotificationsEnabled,
        Boolean smsNotificationsEnabled
) {
}
