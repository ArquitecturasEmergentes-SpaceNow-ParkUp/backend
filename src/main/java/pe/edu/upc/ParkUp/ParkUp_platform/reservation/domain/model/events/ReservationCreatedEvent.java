package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.events;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Domain event fired when a reservation is created
 */
@Getter
public final class ReservationCreatedEvent {
    private final Long reservationId;
    private final Long userId;
    private final Long parkingLotId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final LocalDateTime occurredOn;

    public ReservationCreatedEvent(Long reservationId, Long userId, Long parkingLotId,
                                   LocalDateTime startTime, LocalDateTime endTime) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.parkingLotId = parkingLotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.occurredOn = LocalDateTime.now();
    }
}
