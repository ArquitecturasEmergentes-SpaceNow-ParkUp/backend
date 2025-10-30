package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.queries;

/**
 * Query to get a user profile by document ID (DNI)
 *
 * @param documentNumber The document number
 */
public record GetUserProfileByDocumentIdQuery(String documentNumber) {
}
