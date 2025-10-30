package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands.*;

import java.util.Optional;

/**
 * Reservation command service interface
 * Handles all write operations for reservations
 */
public interface ReservationCommandService {
    
    /**
     * Creates a new reservation
     *
     * @param command The create reservation command
     * @return The created reservation
     */
    Optional<Reservation> handle(CreateReservationCommand command);
    
    /**
     * Cancels an existing reservation
     *
     * @param command The cancel reservation command
     * @return The cancelled reservation
     */
    Optional<Reservation> handle(CancelReservationCommand command);
    
    /**
     * Confirms payment for a reservation
     *
     * @param command The confirm payment command
     * @return The updated reservation
     */
    Optional<Reservation> handle(ConfirmPaymentCommand command);
    
    /**
     * Starts a reservation when user arrives
     *
     * @param command The start reservation command
     * @return The updated reservation
     */
    Optional<Reservation> handle(StartReservationCommand command);
    
    /**
     * Completes a reservation when user departs
     *
     * @param command The complete reservation command
     * @return The completed reservation
     */
    Optional<Reservation> handle(CompleteReservationCommand command);
}
