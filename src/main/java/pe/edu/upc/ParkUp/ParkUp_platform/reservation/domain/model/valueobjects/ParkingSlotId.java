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
    
    protected ParkingSlotId() {
    }
    
    public ParkingSlotId(Long parkingLotId) {
        if (parkingLotId == null || parkingLotId <= 0) {
            throw new IllegalArgumentException("Parking lot ID must be a positive number");
        }
        this.parkingLotId = parkingLotId;
    }
}
