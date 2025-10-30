package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.events;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Domain event fired when a reservation is cancelled
 */
@Getter
public final class ReservationCancelledEvent {
    private final Long reservationId;
    private final Long userId;
    private final Long parkingLotId;
    private final LocalDateTime occurredOn;

    public ReservationCancelledEvent(Long reservationId, Long userId, Long parkingLotId) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.parkingLotId = parkingLotId;
        this.occurredOn = LocalDateTime.now();
    }
}
