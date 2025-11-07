package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddParkingLotMapResource {

    @NotNull
    private Long parkingLotId;

    @NotNull
    private String layoutJson;

    @Min(0)
    private int totalSpaces;

    @Min(0)
    private int disabilitySpaces;

    public AddParkingLotMapResource() {}

    public AddParkingLotMapResource(Long parkingLotId, String layoutJson, int totalSpaces, int disabilitySpaces) {
        this.parkingLotId = parkingLotId;
        this.layoutJson = layoutJson;
        this.totalSpaces = totalSpaces;
        this.disabilitySpaces = disabilitySpaces;
    }

    public Long getParkingLotId() { return parkingLotId; }
    public void setParkingLotId(Long parkingLotId) { this.parkingLotId = parkingLotId; }

    public String getLayoutJson() { return layoutJson; }
    public void setLayoutJson(String layoutJson) { this.layoutJson = layoutJson; }

    public int getTotalSpaces() { return totalSpaces; }
    public void setTotalSpaces(int totalSpaces) { this.totalSpaces = totalSpaces; }

    public int getDisabilitySpaces() { return disabilitySpaces; }
    public void setDisabilitySpaces(int disabilitySpaces) { this.disabilitySpaces = disabilitySpaces; }
}
