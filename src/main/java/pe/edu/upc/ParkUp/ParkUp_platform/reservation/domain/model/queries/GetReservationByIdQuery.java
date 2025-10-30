package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries;

/**
 * Query to get a reservation by ID
 *
 * @param reservationId The ID of the reservation
 */
public record GetReservationByIdQuery(Long reservationId) {
}
