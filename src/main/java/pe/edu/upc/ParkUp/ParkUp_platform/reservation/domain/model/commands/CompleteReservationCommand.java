package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands;

/**
 * Command to complete a reservation when user departs
 *
 * @param reservationId The ID of the reservation
 * @param userId        The ID of the user departing
 */
public record CompleteReservationCommand(Long reservationId, Long userId) {
}
