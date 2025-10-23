package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * ProcessAutoExitCommand
 * <p>
 *     This command represents the request to process an automatic exit
 *     of a vehicle from the parking lot.
 * </p>
 */
public record ProcessAutoExitCommand(
    @NotBlank String licensePlate,
    @NotNull Long recognitionUnitId
) {
    
    /**
     * Create a new ProcessAutoExitCommand
     * @param licensePlate the license plate of the vehicle
     * @param recognitionUnitId the ID of the recognition unit
     * @return new ProcessAutoExitCommand instance
     */
    public static ProcessAutoExitCommand of(String licensePlate, Long recognitionUnitId) {
        return new ProcessAutoExitCommand(licensePlate, recognitionUnitId);
    }
}
