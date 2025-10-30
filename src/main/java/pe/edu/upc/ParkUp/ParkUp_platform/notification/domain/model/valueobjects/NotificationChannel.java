package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects;

/**
 * Notification channel enum
 * Represents the different channels through which notifications can be sent
 */
public enum NotificationChannel {
    /**
     * Push notification to mobile device
     */
    PUSH,
    
    /**
     * Email notification
     */
    EMAIL,
    
    /**
     * WhatsApp message via Twilio
     */
    WHATSAPP
}
