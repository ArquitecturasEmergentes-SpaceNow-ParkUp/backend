package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.commands;

/**
 * Command to create a new user profile
 *
 * @param userId      The ID of the user
 * @param firstName   The user's first name
 * @param lastName    The user's last name
 * @param dni         The user's DNI (8 digits)
 * @param countryCode The country code for phone (e.g., "+51")
 * @param phoneNumber The phone number (digits only)
 */
public record CreateUserProfileCommand(
        Long userId,
        String firstName,
        String lastName,
        String dni,
        String countryCode,
        String phoneNumber
) {
}
