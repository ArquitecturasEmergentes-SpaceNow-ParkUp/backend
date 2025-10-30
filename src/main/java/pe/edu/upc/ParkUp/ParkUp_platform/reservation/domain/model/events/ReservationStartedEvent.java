package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.events;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Domain event fired when a reservation is started (user arrives)
 */
@Getter
public final class ReservationStartedEvent {
    private final Long reservationId;
    private final Long userId;
    private final Long parkingLotId;
    private final LocalDateTime actualStartTime;
    private final LocalDateTime occurredOn;

    public ReservationStartedEvent(Long reservationId, Long userId, Long parkingLotId,
                                   LocalDateTime actualStartTime) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.parkingLotId = parkingLotId;
        this.actualStartTime = actualStartTime;
        this.occurredOn = LocalDateTime.now();
    }
}
