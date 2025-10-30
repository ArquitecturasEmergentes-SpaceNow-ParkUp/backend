package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.events;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Domain event fired when a reservation is completed (user departs)
 */
@Getter
public final class ReservationCompletedEvent {
    private final Long reservationId;
    private final Long userId;
    private final Long parkingLotId;
    private final LocalDateTime actualEndTime;
    private final LocalDateTime occurredOn;

    public ReservationCompletedEvent(Long reservationId, Long userId, Long parkingLotId,
                                     LocalDateTime actualEndTime) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.parkingLotId = parkingLotId;
        this.actualEndTime = actualEndTime;
        this.occurredOn = LocalDateTime.now();
    }
}
