package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries;

import java.time.LocalDateTime;

/**
 * Query to get reservations that need reminders
 *
 * @param afterTime  Start of time range
 * @param beforeTime End of time range
 */
public record GetReservationsNeedingRemindersQuery(LocalDateTime afterTime, LocalDateTime beforeTime) {
}
