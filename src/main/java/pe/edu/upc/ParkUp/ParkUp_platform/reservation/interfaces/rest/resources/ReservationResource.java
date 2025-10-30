package pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Resource representing a reservation in API responses
 *
 * @param id              The reservation ID
 * @param userId          The user ID
 * @param parkingLotId    The parking lot ID
 * @param status          The reservation status
 * @param startTime       The planned start time
 * @param endTime         The planned end time
 * @param actualStartTime The actual start time (null if not started)
 * @param actualEndTime   The actual end time (null if not completed)
 * @param createdAt       Creation timestamp
 * @param updatedAt       Last update timestamp
 */
public record ReservationResource(
        Long id,
        Long userId,
        Long parkingLotId,
        String status,
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocalDateTime actualStartTime,
        LocalDateTime actualEndTime,
        java.util.Date createdAt,
        java.util.Date updatedAt
) {
}
