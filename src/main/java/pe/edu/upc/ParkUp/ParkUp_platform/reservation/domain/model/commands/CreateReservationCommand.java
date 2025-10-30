package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands;

import java.time.LocalDateTime;

/**
 * Command to create a new reservation
 *
 * @param userId        The ID of the user making the reservation
 * @param parkingLotId  The ID of the parking lot to reserve
 * @param startTime     The planned start time of the reservation
 * @param endTime       The planned end time of the reservation
 */
public record CreateReservationCommand(
        Long userId,
        Long parkingLotId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
