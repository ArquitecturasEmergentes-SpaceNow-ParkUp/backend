package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * CreateRecognitionUnitRequest
 * <p>
 *     This resource represents the create recognition unit request data transfer object.
 * </p>
 */
public record CreateRecognitionUnitRequest(
    @NotBlank String identifier,
    @NotNull String location,
    @NotNull Long affiliateId,
    @NotNull Long parkingLotId,
    String description
) {
    
    /**
     * Create a new CreateRecognitionUnitRequest
     * @param identifier the identifier
     * @param location the location
     * @param affiliateId the affiliate ID
     * @param parkingLotId the parking lot ID
     * @param description the description
     * @return new CreateRecognitionUnitRequest instance
     */
    public static CreateRecognitionUnitRequest of(String identifier, String location, 
                                                Long affiliateId, Long parkingLotId, 
                                                String description) {
        return new CreateRecognitionUnitRequest(identifier, location, affiliateId, parkingLotId, description);
    }
}
