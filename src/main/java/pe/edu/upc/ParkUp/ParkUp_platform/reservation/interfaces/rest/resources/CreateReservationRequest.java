package pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.rest.resources;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Request to create a new reservation
 *
 * @param userId        The ID of the user making the reservation
 * @param parkingLotId  The ID of the parking lot to reserve
 * @param startTime     The planned start time of the reservation
 * @param endTime       The planned end time of the reservation
 */
public record CreateReservationRequest(
        @NotNull(message = "User ID is required")
        Long userId,

        @NotNull(message = "Parking lot ID is required")
        Long parkingLotId,

        @NotNull(message = "Start time is required")
        @Future(message = "Start time must be in the future")
        LocalDateTime startTime,

        @NotNull(message = "End time is required")
        @Future(message = "End time must be in the future")
        LocalDateTime endTime
) {
}
