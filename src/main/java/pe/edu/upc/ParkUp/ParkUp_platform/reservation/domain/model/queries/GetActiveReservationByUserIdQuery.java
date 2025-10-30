package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries;

/**
 * Query to get active reservation for a user
 *
 * @param userId The ID of the user
 */
public record GetActiveReservationByUserIdQuery(Long userId) {
}
