package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ProcessAutoExitCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.AutoExitRequest;

/**
 * ProcessAutoExitCommandFromRequestAssembler
 * <p>
 *     This class is responsible for transforming AutoExitRequest DTOs to ProcessAutoExitCommand domain objects.
 * </p>
 */
public class ProcessAutoExitCommandFromRequestAssembler {

    /**
     * Transform AutoExitRequest to ProcessAutoExitCommand
     * @param request the auto exit request
     * @return the process auto exit command
     */
    public static ProcessAutoExitCommand toCommandFromRequest(AutoExitRequest request) {
        if (request == null) {
            return null;
        }

        return ProcessAutoExitCommand.of(
                request.licensePlate(),
                request.recognitionUnitId()
        );
    }
}
