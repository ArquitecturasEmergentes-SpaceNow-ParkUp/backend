package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * User preferences for notifications
 * Value object representing which channels a user wants to receive notifications on
 */
@Embeddable
@Getter
public class NotificationPreferences {
    
    private Boolean pushEnabled;
    private Boolean emailEnabled;
    private Boolean whatsappEnabled;
    
    protected NotificationPreferences() {
        this.pushEnabled = false;
        this.emailEnabled = false;
        this.whatsappEnabled = true; // WhatsApp typically opt-in
    }

    public NotificationPreferences(Boolean pushEnabled, Boolean emailEnabled, Boolean whatsappEnabled) {
        this.pushEnabled = pushEnabled != null ? pushEnabled : true;
        this.emailEnabled = emailEnabled != null ? emailEnabled : true;
        this.whatsappEnabled = whatsappEnabled != null ? whatsappEnabled : false;
    }
    
    /**
     * Checks if a specific channel is enabled
     */
    public boolean isChannelEnabled(NotificationChannel channel) {
        return switch (channel) {
            case PUSH -> Boolean.TRUE.equals(pushEnabled);
            case EMAIL -> Boolean.TRUE.equals(emailEnabled);
            case WHATSAPP -> Boolean.TRUE.equals(whatsappEnabled);
        };
    }
}
