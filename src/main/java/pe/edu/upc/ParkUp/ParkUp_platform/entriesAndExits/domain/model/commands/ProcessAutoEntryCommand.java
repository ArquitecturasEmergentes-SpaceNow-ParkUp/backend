package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * ProcessAutoEntryCommand
 * <p>
 *     This command represents the request to process an automatic entry
 *     of a vehicle into the parking lot.
 * </p>
 */
public record ProcessAutoEntryCommand(
    @NotBlank String licensePlate,
    @NotNull Long recognitionUnitId,
    @NotNull Long userId
) {
    
    /**
     * Create a new ProcessAutoEntryCommand
     * @param licensePlate the license plate of the vehicle
     * @param recognitionUnitId the ID of the recognition unit
     * @param userId the ID of the user
     * @return new ProcessAutoEntryCommand instance
     */
    public static ProcessAutoEntryCommand of(String licensePlate, Long recognitionUnitId, Long userId) {
        return new ProcessAutoEntryCommand(licensePlate, recognitionUnitId, userId);
    }
}
