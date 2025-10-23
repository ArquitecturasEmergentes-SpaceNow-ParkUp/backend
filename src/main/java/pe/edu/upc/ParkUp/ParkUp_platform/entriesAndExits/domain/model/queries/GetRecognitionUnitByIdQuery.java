package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries;

import jakarta.validation.constraints.NotNull;

/**
 * GetRecognitionUnitByIdQuery
 * <p>
 *     This query represents the request to get a recognition unit by its ID.
 * </p>
 */
public record GetRecognitionUnitByIdQuery(
    @NotNull Long recognitionUnitId
) {
    
    /**
     * Create a new GetRecognitionUnitByIdQuery
     * @param recognitionUnitId the ID of the recognition unit
     * @return new GetRecognitionUnitByIdQuery instance
     */
    public static GetRecognitionUnitByIdQuery of(Long recognitionUnitId) {
        return new GetRecognitionUnitByIdQuery(recognitionUnitId);
    }
}
