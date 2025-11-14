package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;

public class UpdateParkingSpaceStatusResource {
    @NotNull
    private ParkingSpace.SpaceStatus status;

    public UpdateParkingSpaceStatusResource() {}
    public UpdateParkingSpaceStatusResource(ParkingSpace.SpaceStatus status) { this.status = status; }
    public ParkingSpace.SpaceStatus getStatus() { return status; }
    public void setStatus(ParkingSpace.SpaceStatus status) { this.status = status; }
}

