package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ControlBarrierCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.BarrierControlRequest;

/**
 * ControlBarrierCommandFromRequestAssembler
 * <p>
 *     This class is responsible for transforming BarrierControlRequest DTOs to ControlBarrierCommand domain objects.
 * </p>
 */
public class ControlBarrierCommandFromRequestAssembler {

    /**
     * Transform BarrierControlRequest to ControlBarrierCommand
     * @param request the barrier control request
     * @return the control barrier command
     */
    public static ControlBarrierCommand toCommandFromRequest(BarrierControlRequest request) {
        if (request == null) {
            return null;
        }

        ControlBarrierCommand.BarrierAction action;
        try {
            action = ControlBarrierCommand.BarrierAction.valueOf(request.action().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid barrier action: " + request.action());
        }

        return ControlBarrierCommand.of(
                request.recognitionUnitId(),
                action
        );
    }
}
