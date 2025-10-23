package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ProcessAutoEntryCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.AutoEntryRequest;

/**
 * ProcessAutoEntryCommandFromRequestAssembler
 * <p>
 *     This class is responsible for transforming AutoEntryRequest DTOs to ProcessAutoEntryCommand domain objects.
 * </p>
 */
public class ProcessAutoEntryCommandFromRequestAssembler {

    /**
     * Transform AutoEntryRequest to ProcessAutoEntryCommand
     * @param request the auto entry request
     * @return the process auto entry command
     */
    public static ProcessAutoEntryCommand toCommandFromRequest(AutoEntryRequest request) {
        if (request == null) {
            return null;
        }

        return ProcessAutoEntryCommand.of(
                request.licensePlate(),
                request.recognitionUnitId(),
                request.userId()
        );
    }
}
