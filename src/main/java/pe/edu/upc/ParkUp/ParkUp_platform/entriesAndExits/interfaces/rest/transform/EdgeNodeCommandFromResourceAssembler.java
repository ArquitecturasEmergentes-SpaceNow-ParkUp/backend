package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;


import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.RegisterEdgeNodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeResource;

public class EdgeNodeCommandFromResourceAssembler {

    public static RegisterEdgeNodeCommand toCommandFromResource(EdgeNodeResource resource) {
        return new RegisterEdgeNodeCommand(
                resource.ipAddress(),
                resource.port(),
                resource.identifier(),
                resource.affiliateId(),
                resource.parkingLotId()
        );
    }
}