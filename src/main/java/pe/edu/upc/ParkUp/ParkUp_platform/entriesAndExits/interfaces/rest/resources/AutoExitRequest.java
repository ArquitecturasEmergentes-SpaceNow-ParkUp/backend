package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * AutoExitRequest
 * <p>
 *     This resource represents the auto exit request data transfer object.
 * </p>
 */
public record AutoExitRequest(
    @NotBlank String licensePlate,
    @NotNull Long recognitionUnitId
) {
    
    /**
     * Create a new AutoExitRequest
     * @param licensePlate the license plate
     * @param recognitionUnitId the recognition unit ID
     * @return new AutoExitRequest instance
     */
    public static AutoExitRequest of(String licensePlate, Long recognitionUnitId) {
        return new AutoExitRequest(licensePlate, recognitionUnitId);
    }
}
