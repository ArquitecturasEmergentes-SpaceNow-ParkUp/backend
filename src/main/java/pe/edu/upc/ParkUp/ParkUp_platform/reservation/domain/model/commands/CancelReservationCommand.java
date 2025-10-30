package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands;

/**
 * Command to cancel a reservation
 *
 * @param reservationId The ID of the reservation to cancel
 */
public record CancelReservationCommand(Long reservationId) {
}
