package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.events;

public final class MapEditedEvent {
    private final Long mapId;
    private final Long parkingLotId;
    public MapEditedEvent(Long mapId, Long parkingLotId) {
        this.mapId = mapId; this.parkingLotId = parkingLotId;
    }
    public Long mapId() { return mapId; }
    public Long parkingLotId() { return parkingLotId; }
}