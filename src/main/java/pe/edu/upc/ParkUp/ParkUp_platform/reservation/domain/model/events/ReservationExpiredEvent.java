package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.events;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Domain event fired when a reservation is expired
 */
@Getter
public final class ReservationExpiredEvent {
    private final Long reservationId;
    private final Long userId;
    private final LocalDateTime occurredOn;

    public ReservationExpiredEvent(Long reservationId, Long userId) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.occurredOn = LocalDateTime.now();
    }
}
