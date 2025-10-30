package pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands.CompleteReservationCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands.StartReservationCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries.GetActiveReservationByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.services.ReservationCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.services.ReservationQueryService;

/**
 * Anti-Corruption Layer for Reservation bounded context
 * Provides a facade for other bounded contexts to interact with reservations
 */
@Service
public class ReservationContextFacade {

    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;

    public ReservationContextFacade(ReservationCommandService reservationCommandService,
                                    ReservationQueryService reservationQueryService) {
        this.reservationCommandService = reservationCommandService;
        this.reservationQueryService = reservationQueryService;
    }

    /**
     * Handles user arrival event from EntriesAndExits BC
     * Starts the active reservation for the user
     *
     * @param userId The user ID
     * @return true if reservation was started successfully
     */
    public boolean handleUserArrival(Long userId) {
        try {
            var query = new GetActiveReservationByUserIdQuery(userId);
            var reservation = reservationQueryService.handle(query);

            if (reservation.isEmpty()) {
                System.out.println("No active reservation found for user " + userId);
                return false;
            }

            var command = new StartReservationCommand(reservation.get().getId(), userId);
            var result = reservationCommandService.handle(command);

            return result.isPresent();
        } catch (Exception e) {
            System.err.println("Error handling user arrival: " + e.getMessage());
            return false;
        }
    }

    /**
     * Handles user departure event from EntriesAndExits BC
     * Completes the in-progress reservation for the user
     *
     * @param userId The user ID
     * @return true if reservation was completed successfully
     */
    public boolean handleUserDeparture(Long userId) {
        try {
            var query = new GetActiveReservationByUserIdQuery(userId);
            var reservation = reservationQueryService.handle(query);

            if (reservation.isEmpty()) {
                System.out.println("No active reservation found for user " + userId);
                return false;
            }

            var command = new CompleteReservationCommand(reservation.get().getId(), userId);
            var result = reservationCommandService.handle(command);

            return result.isPresent();
        } catch (Exception e) {
            System.err.println("Error handling user departure: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a user has an active reservation
     *
     * @param userId The user ID
     * @return true if user has an active or in-progress reservation
     */
    public boolean hasActiveReservation(Long userId) {
        var query = new GetActiveReservationByUserIdQuery(userId);
        var reservation = reservationQueryService.handle(query);
        return reservation.isPresent();
    }
}
