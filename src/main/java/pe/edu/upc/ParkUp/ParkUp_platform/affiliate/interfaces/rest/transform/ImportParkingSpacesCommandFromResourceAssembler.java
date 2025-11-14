package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.ImportParkingSpacesCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.ImportParkingSpacesResource;

import java.util.List;

public final class ImportParkingSpacesCommandFromResourceAssembler {
    private ImportParkingSpacesCommandFromResourceAssembler() {}

    public static ImportParkingSpacesCommand toCommand(ImportParkingSpacesResource r) {
        List<ImportParkingSpacesCommand.SpaceItem> items = r.getItems().stream()
                .map(i -> new ImportParkingSpacesCommand.SpaceItem(i.getCode(), i.isDisability()))
                .toList();
        return new ImportParkingSpacesCommand(r.getMapId(), items);
    }
}

