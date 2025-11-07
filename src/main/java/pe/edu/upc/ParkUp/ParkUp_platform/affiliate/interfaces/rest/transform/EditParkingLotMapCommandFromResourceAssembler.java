package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform;

import java.util.Objects;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.EditParkingLotMapResource;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.EditParkingLotMapCommand;

public final class EditParkingLotMapCommandFromResourceAssembler {

    private EditParkingLotMapCommandFromResourceAssembler() {}

    public static EditParkingLotMapCommand toCommand(EditParkingLotMapResource r) {
        Objects.requireNonNull(r, "resource no puede ser null");
        return new EditParkingLotMapCommand(
                r.getParkingLotId(),
                r.getMapId(),
                r.getNewLayoutJson(),
                r.getNewTotalSpaces(),
                r.getNewDisabilitySpaces()
        );
    }
}
