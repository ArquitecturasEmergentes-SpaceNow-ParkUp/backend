package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries;

import jakarta.validation.constraints.NotNull;

/**
 * GetAllRecognitionUnitsByAffiliateQuery
 * <p>
 *     This query represents the request to get all recognition units by affiliate ID.
 * </p>
 */
public record GetAllRecognitionUnitsByAffiliateQuery(
    @NotNull Long affiliateId
) {
    
    /**
     * Create a new GetAllRecognitionUnitsByAffiliateQuery
     * @param affiliateId the ID of the affiliate
     * @return new GetAllRecognitionUnitsByAffiliateQuery instance
     */
    public static GetAllRecognitionUnitsByAffiliateQuery of(Long affiliateId) {
        return new GetAllRecognitionUnitsByAffiliateQuery(affiliateId);
    }
}
