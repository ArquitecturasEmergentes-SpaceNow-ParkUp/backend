package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries;

/**
 * Query to get all reservations for a specific user
 *
 * @param userId The ID of the user
 */
public record GetReservationsByUserIdQuery(Long userId) {
}
