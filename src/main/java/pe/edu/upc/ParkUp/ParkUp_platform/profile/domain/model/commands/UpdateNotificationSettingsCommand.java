package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.commands;

/**
 * Command to update notification settings
 *
 * @param profileId                 The ID of the profile to update
 * @param notificationsEnabled      Enable/disable all notifications
 * @param emailNotificationsEnabled Enable/disable email notifications
 * @param smsNotificationsEnabled   Enable/disable SMS notifications
 */
public record UpdateNotificationSettingsCommand(
        Long profileId,
        Boolean notificationsEnabled,
        Boolean emailNotificationsEnabled,
        Boolean smsNotificationsEnabled
) {
}
