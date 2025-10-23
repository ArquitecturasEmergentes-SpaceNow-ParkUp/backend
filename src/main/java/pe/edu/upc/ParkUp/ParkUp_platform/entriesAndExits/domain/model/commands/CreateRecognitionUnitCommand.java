package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;

/**
 * CreateRecognitionUnitCommand
 * <p>
 *     This command represents the request to create a new recognition unit.
 * </p>
 */
public record CreateRecognitionUnitCommand(
    @NotBlank String identifier,
    @NotNull RecognitionUnit.Location location,
    @NotNull Long affiliateId,
    @NotNull Long parkingLotId,
    String description
) {
    
    /**
     * Create a new CreateRecognitionUnitCommand
     * @param identifier the unique identifier of the recognition unit
     * @param location the location of the recognition unit
     * @param affiliateId the ID of the affiliate
     * @param parkingLotId the ID of the parking lot
     * @param description optional description
     * @return new CreateRecognitionUnitCommand instance
     */
    public static CreateRecognitionUnitCommand of(String identifier, 
                                                  RecognitionUnit.Location location, 
                                                  Long affiliateId, 
                                                  Long parkingLotId, 
                                                  String description) {
        return new CreateRecognitionUnitCommand(identifier, location, affiliateId, parkingLotId, description);
    }
}
