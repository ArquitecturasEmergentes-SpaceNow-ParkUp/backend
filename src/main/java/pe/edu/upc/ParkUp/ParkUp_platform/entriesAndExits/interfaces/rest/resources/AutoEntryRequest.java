package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * AutoEntryRequest
 * <p>
 *     This resource represents the auto entry request data transfer object.
 * </p>
 */
public record AutoEntryRequest(
    @NotBlank String licensePlate,
    @NotNull Long recognitionUnitId,
    @NotNull Long userId
) {
    
    /**
     * Create a new AutoEntryRequest
     * @param licensePlate the license plate
     * @param recognitionUnitId the recognition unit ID
     * @param userId the user ID
     * @return new AutoEntryRequest instance
     */
    public static AutoEntryRequest of(String licensePlate, Long recognitionUnitId, Long userId) {
        return new AutoEntryRequest(licensePlate, recognitionUnitId, userId);
    }
}
