package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.CreateExtraordinaryCaseCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.resources.CreateExtraordinaryCaseResource;

public class CreateExtraordinaryCaseCommandFromResourceAssembler {
    public static CreateExtraordinaryCaseCommand toCommandFromResource(CreateExtraordinaryCaseResource resource) {
        return new CreateExtraordinaryCaseCommand(
                resource.parkingLotId(),
                resource.recognitionUnitId(),
                resource.plateNumber(),
                resource.caseReason()
        );
    }
}