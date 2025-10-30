package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.queries;

/**
 * Query to get a user profile by its ID
 *
 * @param profileId The ID of the profile
 */
public record GetUserProfileByIdQuery(Long profileId) {
}
