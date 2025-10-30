package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries;

import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.valueobjects.ReservationStatus;

/**
 * Query to get reservations by status
 *
 * @param status The status to filter by
 */
public record GetReservationsByStatusQuery(ReservationStatus status) {
}
