package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.RegisterEdgeNodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.RegisterEdgeNodeRequest;

/**
 * RegisterEdgeNodeCommandFromRequestAssembler
 * <p>
 *     This class is responsible for transforming RegisterEdgeNodeRequest DTOs to RegisterEdgeNodeCommand domain objects.
 * </p>
 */
public class RegisterEdgeNodeCommandFromRequestAssembler {

    /**
     * Transform RegisterEdgeNodeRequest to RegisterEdgeNodeCommand
     * @param request the register edge node request
     * @return the register edge node command
     */
    public static RegisterEdgeNodeCommand toCommandFromRequest(RegisterEdgeNodeRequest request) {
        if (request == null) {
            return null;
        }

        return RegisterEdgeNodeCommand.of(
                request.ipAddress(),
                request.port(),
                request.identifier(),
                request.affiliateId(),
                request.parkingLotId()
        );
    }
}
