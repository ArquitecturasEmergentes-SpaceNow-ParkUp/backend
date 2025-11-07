package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform;

import java.util.Objects;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.CreateParkingLotResource;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.CreateParkingLotCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.MapLayout;

public final class CreateParkingLotCommandFromResourceAssembler {

    private CreateParkingLotCommandFromResourceAssembler() {}

    public static CreateParkingLotCommand toCommand(CreateParkingLotResource r) {
        Objects.requireNonNull(r, "resource no puede ser null");
        MapLayout layout = null;
        CreateParkingLotResource.MapLayoutResource m = r.getInitialMap();
        if (m != null) {
            layout = new MapLayout(m.getTotalSpaces(), m.getDisabilitySpaces(), m.getLayoutJson());
        }
        return new CreateParkingLotCommand(r.getName(), r.getAddress(), layout);
    }
}
