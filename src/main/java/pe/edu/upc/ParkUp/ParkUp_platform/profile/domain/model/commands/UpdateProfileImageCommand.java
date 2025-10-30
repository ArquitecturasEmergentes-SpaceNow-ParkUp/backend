package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.commands;

/**
 * Command to update user profile image
 *
 * @param profileId The ID of the profile to update
 * @param imageUrl  The new profile image URL
 */
public record UpdateProfileImageCommand(
        Long profileId,
        String imageUrl
) {
}
