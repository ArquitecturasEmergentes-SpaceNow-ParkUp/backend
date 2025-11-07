package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EditParkingLotMapResource {

    @NotNull
    private Long parkingLotId;

    @NotNull
    private Long mapId;

    @NotNull
    private String newLayoutJson;

    @Min(0)
    private int newTotalSpaces;

    @Min(0)
    private int newDisabilitySpaces;

    public EditParkingLotMapResource() {}

    public EditParkingLotMapResource(Long parkingLotId, Long mapId, String newLayoutJson, int newTotalSpaces, int newDisabilitySpaces) {
        this.parkingLotId = parkingLotId;
        this.mapId = mapId;
        this.newLayoutJson = newLayoutJson;
        this.newTotalSpaces = newTotalSpaces;
        this.newDisabilitySpaces = newDisabilitySpaces;
    }

    public Long getParkingLotId() { return parkingLotId; }
    public void setParkingLotId(Long parkingLotId) { this.parkingLotId = parkingLotId; }

    public Long getMapId() { return mapId; }
    public void setMapId(Long mapId) { this.mapId = mapId; }

    public String getNewLayoutJson() { return newLayoutJson; }
    public void setNewLayoutJson(String newLayoutJson) { this.newLayoutJson = newLayoutJson; }

    public int getNewTotalSpaces() { return newTotalSpaces; }
    public void setNewTotalSpaces(int newTotalSpaces) { this.newTotalSpaces = newTotalSpaces; }

    public int getNewDisabilitySpaces() { return newDisabilitySpaces; }
    public void setNewDisabilitySpaces(int newDisabilitySpaces) { this.newDisabilitySpaces = newDisabilitySpaces; }
}
