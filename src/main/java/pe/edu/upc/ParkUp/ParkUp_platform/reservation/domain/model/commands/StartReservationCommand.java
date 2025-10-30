package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands;

/**
 * Command to start a reservation when user arrives
 *
 * @param reservationId The ID of the reservation
 * @param userId        The ID of the user arriving
 */
public record StartReservationCommand(Long reservationId, Long userId) {
}
