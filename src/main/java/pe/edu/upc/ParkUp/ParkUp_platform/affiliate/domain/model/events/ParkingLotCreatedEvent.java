package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.events;

public final class ParkingLotCreatedEvent {
    private final Long parkingLotId;
    public ParkingLotCreatedEvent(Long parkingLotId) { this.parkingLotId = parkingLotId; }
    public Long parkingLotId() { return parkingLotId; }
}