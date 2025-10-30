package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.commands;

/**
 * Command to update an existing user profile
 *
 * @param profileId   The ID of the profile to update
 * @param firstName   The user's first name
 * @param lastName    The user's last name
 * @param dni         The user's DNI (8 digits)
 * @param countryCode The country code for phone (e.g., "+51")
 * @param phoneNumber The phone number (digits only)
 */
public record UpdateUserProfileCommand(
        Long profileId,
        String firstName,
        String lastName,
        String dni,
        String countryCode,
        String phoneNumber
) {
}
