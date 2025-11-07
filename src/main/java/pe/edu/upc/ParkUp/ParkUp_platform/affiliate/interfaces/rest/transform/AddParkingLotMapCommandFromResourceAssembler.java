package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform;

import java.util.Objects;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.AddParkingLotMapResource;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.AddParkingLotMapCommand;

public final class AddParkingLotMapCommandFromResourceAssembler {

    private AddParkingLotMapCommandFromResourceAssembler() {}

    public static AddParkingLotMapCommand toCommand(AddParkingLotMapResource r) {
        Objects.requireNonNull(r, "resource no puede ser null");
        return new AddParkingLotMapCommand(
                r.getParkingLotId(),
                r.getLayoutJson(),
                r.getTotalSpaces(),
                r.getDisabilitySpaces()
        );
    }
}
