package pe.edu.upc.ParkUp.ParkUp_platform.reservation.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Reservation bounded context
 * These properties can be overridden in application.properties
 */
@Configuration
@ConfigurationProperties(prefix = "reservation")
public class ReservationConfiguration {

    /**
     * Minimum hours before start time required for cancellation
     * Default: 1 hour
     */
    private int cancellationPolicyHours = 1;

    /**
     * Minutes before start time to send reminder notification
     * Default: 15 minutes
     */
    private int reminderMinutesBefore = 15;

    /**
     * Fixed rate in milliseconds for reminder check task
     * Default: 5 minutes (300000 ms)
     */
    private long reminderCheckRate = 300000;

    /**
     * Fixed rate in milliseconds for expiration check task
     * Default: 1 minute (60000 ms)
     */
    private long expirationCheckRate = 60000;

    /**
     * Enable or disable scheduled tasks
     * Default: true
     */
    private boolean scheduledTasksEnabled = true;

    // Getters and setters
    public int getCancellationPolicyHours() {
        return cancellationPolicyHours;
    }

    public void setCancellationPolicyHours(int cancellationPolicyHours) {
        this.cancellationPolicyHours = cancellationPolicyHours;
    }

    public int getReminderMinutesBefore() {
        return reminderMinutesBefore;
    }

    public void setReminderMinutesBefore(int reminderMinutesBefore) {
        this.reminderMinutesBefore = reminderMinutesBefore;
    }

    public long getReminderCheckRate() {
        return reminderCheckRate;
    }

    public void setReminderCheckRate(long reminderCheckRate) {
        this.reminderCheckRate = reminderCheckRate;
    }

    public long getExpirationCheckRate() {
        return expirationCheckRate;
    }

    public void setExpirationCheckRate(long expirationCheckRate) {
        this.expirationCheckRate = expirationCheckRate;
    }

    public boolean isScheduledTasksEnabled() {
        return scheduledTasksEnabled;
    }

    public void setScheduledTasksEnabled(boolean scheduledTasksEnabled) {
        this.scheduledTasksEnabled = scheduledTasksEnabled;
    }
}
