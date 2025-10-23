package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.CreateRecognitionUnitCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.CreateRecognitionUnitRequest;

/**
 * CreateRecognitionUnitCommandFromRequestAssembler
 * <p>
 *     This class is responsible for transforming CreateRecognitionUnitRequest DTOs to CreateRecognitionUnitCommand domain objects.
 * </p>
 */
public class CreateRecognitionUnitCommandFromRequestAssembler {

    /**
     * Transform CreateRecognitionUnitRequest to CreateRecognitionUnitCommand
     * @param request the create recognition unit request
     * @return the create recognition unit command
     */
    public static CreateRecognitionUnitCommand toCommandFromRequest(CreateRecognitionUnitRequest request) {
        if (request == null) {
            return null;
        }

        RecognitionUnit.Location location;
        try {
            location = RecognitionUnit.Location.valueOf(request.location().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid location: " + request.location());
        }

        return CreateRecognitionUnitCommand.of(
                request.identifier(),
                location,
                request.affiliateId(),
                request.parkingLotId(),
                request.description()
        );
    }
}
