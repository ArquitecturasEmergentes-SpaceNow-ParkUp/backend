package pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.valueobjects;

/**
 * Reservation status enum
 * Represents the different states a reservation can have throughout its lifecycle
 */
public enum ReservationStatus {
    /**
     * Reservation created but payment not yet processed
     */
    PENDING_PAYMENT,
    
    /**
     * Payment successful, reservation is active and waiting for user arrival
     */
    ACTIVE,
    
    /**
     * User has arrived and is using the parking slot
     */
    IN_PROGRESS,
    
    /**
     * Reservation completed successfully, user has departed
     */
    COMPLETED,
    
    /**
     * Reservation cancelled by user or system
     */
    CANCELLED,
    
    /**
     * Reservation time expired without user showing up or after exceeding time limit
     */
    EXPIRED
}
