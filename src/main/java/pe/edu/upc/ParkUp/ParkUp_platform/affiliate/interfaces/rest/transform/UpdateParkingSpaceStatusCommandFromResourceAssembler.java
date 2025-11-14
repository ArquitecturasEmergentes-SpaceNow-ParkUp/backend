package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.UpdateParkingSpaceStatusCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.UpdateParkingSpaceStatusResource;

public final class UpdateParkingSpaceStatusCommandFromResourceAssembler {
    private UpdateParkingSpaceStatusCommandFromResourceAssembler() {}

    public static UpdateParkingSpaceStatusCommand toCommand(Long spaceId, UpdateParkingSpaceStatusResource r) {
        return new UpdateParkingSpaceStatusCommand(spaceId, r.getStatus());
    }
}

