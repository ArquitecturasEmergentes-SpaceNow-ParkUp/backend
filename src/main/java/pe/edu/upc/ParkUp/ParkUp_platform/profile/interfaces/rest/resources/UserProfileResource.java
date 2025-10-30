package pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.resources;

/**
 * Resource representing a user profile
 *
 * @param id                        The profile ID
 * @param userId                    The user ID
 * @param firstName                 The user's first name
 * @param lastName                  The user's last name
 * @param fullName                  The user's full name
 * @param dni                       The user's DNI
 * @param countryCode               The country code for phone
 * @param phoneNumber               The phone number
 * @param fullPhoneNumber           The complete phone number with country code
 * @param profileImageUrl           The profile image URL
 * @param notificationsEnabled      Whether notifications are enabled
 * @param emailNotificationsEnabled Whether email notifications are enabled
 * @param smsNotificationsEnabled   Whether SMS notifications are enabled
 */
public record UserProfileResource(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        String fullName,
        String dni,
        String countryCode,
        String phoneNumber,
        String fullPhoneNumber,
        String profileImageUrl,
        Boolean notificationsEnabled,
        Boolean emailNotificationsEnabled,
        Boolean smsNotificationsEnabled
) {
}
