package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * Parking slot identifier value object
 * Represents a reference to a parking slot in the Affiliates bounded context
 */
@Embeddable
@Getter
public class ParkingSlotId {
    
    private Long parkingLotId;
    private Long parkingSpaceId; // specific space ID in affiliate BC
    
    protected ParkingSlotId() {
    }
    
    public ParkingSlotId(Long parkingLotId, Long parkingSpaceId) {
        if (parkingLotId == null || parkingLotId <= 0) {
            throw new IllegalArgumentException("Parking lot ID must be a positive number");
        }
        if (parkingSpaceId == null || parkingSpaceId <= 0) {
            throw new IllegalArgumentException("Parking space ID must be a positive number");
        }
        this.parkingLotId = parkingLotId;
        this.parkingSpaceId = parkingSpaceId;
    }

    public Long getParkingSpaceId() {
        return parkingSpaceId;
    }
}
