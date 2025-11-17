package pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.resources.CreateLogResource;

public class CreateLogCommandFromResourceAssembler {
    
    public static CreateLogCommand toCommandFromResource(CreateLogResource resource) {
        return new CreateLogCommand(
            resource.action(),
            resource.userId(),
            resource.username(),
            resource.userEmail(),
            resource.details(),
            resource.ipAddress(),
            resource.userAgent(),
            resource.resourceType(),
            resource.resourceId(),
            resource.status()
            , resource.requestPayloadHash()
        );
    }
}