package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;


import java.time.LocalDateTime;

/**
 * RecognitionUnitResource
 * <p>
 *     This resource represents the recognition unit data transfer object.
 * </p>
 */
public record RecognitionUnitResource(
    Long id,
    String identifier,
    String location,
    String status,
    Long affiliateId,
    Long parkingLotId,
    String description,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    
    /**
     * Create a new RecognitionUnitResource
     * @param id the recognition unit ID
     * @param identifier the identifier
     * @param location the location
     * @param status the status
     * @param affiliateId the affiliate ID
     * @param parkingLotId the parking lot ID
     * @param description the description
     * @param createdAt the creation date
     * @param updatedAt the update date
     * @return new RecognitionUnitResource instance
     */
    public static RecognitionUnitResource of(Long id, String identifier, String location, 
                                           String status, Long affiliateId, Long parkingLotId, 
                                           String description, LocalDateTime createdAt, 
                                           LocalDateTime updatedAt) {
        return new RecognitionUnitResource(id, identifier, location, status, affiliateId, 
                                         parkingLotId, description, createdAt, updatedAt);
    }
}
