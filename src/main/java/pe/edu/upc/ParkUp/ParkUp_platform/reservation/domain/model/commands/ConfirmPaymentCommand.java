package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands;

/**
 * Command to confirm payment for a reservation
 *
 * @param reservationId The ID of the reservation
 */
public record ConfirmPaymentCommand(Long reservationId) {
}
