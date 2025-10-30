package pe.edu.upc.ParkUp.ParkUp_platform.profile.interfaces.rest.resources;

/**
 * Resource for updating profile image
 *
 * @param imageUrl The new profile image URL
 */
public record UpdateProfileImageResource(
        String imageUrl
) {
}
