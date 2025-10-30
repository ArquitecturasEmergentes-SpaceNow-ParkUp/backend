package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects;

/**
 * Notification status enum
 * Represents the status of a notification delivery attempt
 */
public enum NotificationStatus {
    /**
     * Notification queued, waiting to be sent
     */
    PENDING,
    
    /**
     * Notification successfully sent
     */
    SENT,
    
    /**
     * Notification delivery failed
     */
    FAILED,
    
    /**
     * Notification read by user
     */
    READ
}
