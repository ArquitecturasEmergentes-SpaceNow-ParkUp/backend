package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects;

/**
 * Notification type enum
 * Represents the different types of notifications in the system
 */
public enum NotificationType {
    /**
     * Reservation confirmed after successful payment
     */
    RESERVATION_CONFIRMED,
    
    /**
     * Reminder notification before reservation starts
     */
    RESERVATION_REMINDER,
    
    /**
     * Reservation cancelled
     */
    RESERVATION_CANCELLED,
    
    /**
     * Reservation started (user arrived)
     */
    RESERVATION_STARTED,
    
    /**
     * Reservation completed (user departed)
     */
    RESERVATION_COMPLETED,
    
    /**
     * Reservation expired
     */
    RESERVATION_EXPIRED,
    
    /**
     * Payment successful
     */
    PAYMENT_SUCCESS,
    
    /**
     * Payment failed
     */
    PAYMENT_FAILED,
    
    /**
     * Payment refund processed
     */
    PAYMENT_REFUND,
    
    /**
     * Generic system notification
     */
    SYSTEM_ALERT
}
