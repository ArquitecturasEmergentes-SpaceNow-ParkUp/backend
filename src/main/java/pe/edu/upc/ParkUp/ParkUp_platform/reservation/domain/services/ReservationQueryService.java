package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Reservation query service interface
 * Handles all read operations for reservations
 */
public interface ReservationQueryService {
    
    /**
     * Gets a reservation by ID
     *
     * @param query The get reservation by ID query
     * @return The reservation if found
     */
    Optional<Reservation> handle(GetReservationByIdQuery query);
    
    /**
     * Gets all reservations for a specific user
     *
     * @param query The get reservations by user ID query
     * @return List of reservations for the user
     */
    List<Reservation> handle(GetReservationsByUserIdQuery query);
    
    /**
     * Gets reservations by status
     *
     * @param query The get reservations by status query
     * @return List of reservations with the specified status
     */
    List<Reservation> handle(GetReservationsByStatusQuery query);
    
    /**
     * Gets active reservation for a user
     *
     * @param query The get active reservation by user ID query
     * @return The active reservation if found
     */
    Optional<Reservation> handle(GetActiveReservationByUserIdQuery query);
    
    /**
     * Gets reservations that need reminders
     *
     * @param query The get reservations needing reminders query
     * @return List of reservations needing reminders
     */
    List<Reservation> handle(GetReservationsNeedingRemindersQuery query);
}
