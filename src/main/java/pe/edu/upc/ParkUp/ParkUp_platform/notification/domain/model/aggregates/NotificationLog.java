package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationChannel;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationStatus;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationType;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;

/**
 * NotificationLog aggregate root
 * Represents a log of notification sent to a user
 */
@Entity
@Table(name = "notification_logs")
@Getter
public class NotificationLog extends AuditableAbstractAggregateRoot<NotificationLog> {
    
    @NotNull
    @Column(nullable = false)
    private Long userId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationChannel channel;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationType type;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationStatus status;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(columnDefinition = "text")
    private String message;
    
    @Column(columnDefinition = "text")
    private String metadata; // JSON string with additional data
    
    @Column
    private LocalDateTime sentAt;
    
    @Column
    private LocalDateTime readAt;
    
    @Column(columnDefinition = "text")
    private String errorMessage;
    
    @Column(length = 100)
    private String externalId; // ID from external service (Firebase, SendGrid, etc.)
    
    protected NotificationLog() {
    }
    
    /**
     * Constructor for creating a new notification log
     */
    public NotificationLog(Long userId, NotificationChannel channel, NotificationType type,
                          String title, String message, String metadata) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
        if (channel == null) {
            throw new IllegalArgumentException("Channel is required");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type is required");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        
        this.userId = userId;
        this.channel = channel;
        this.type = type;
        this.title = title;
        this.message = message;
        this.metadata = metadata;
        this.status = NotificationStatus.PENDING;
    }
    
    /**
     * Marks the notification as sent
     */
    public void markAsSent(String externalId) {
        this.status = NotificationStatus.SENT;
        this.sentAt = LocalDateTime.now();
        this.externalId = externalId;
    }
    
    /**
     * Marks the notification as failed
     */
    public void markAsFailed(String errorMessage) {
        this.status = NotificationStatus.FAILED;
        this.errorMessage = errorMessage;
    }
    
    /**
     * Marks the notification as read by user
     */
    public void markAsRead() {
        if (this.status == NotificationStatus.SENT) {
            this.status = NotificationStatus.READ;
            this.readAt = LocalDateTime.now();
        }
    }
    
    /**
     * Retries sending a failed notification
     */
    public void retry() {
        if (this.status == NotificationStatus.FAILED) {
            this.status = NotificationStatus.PENDING;
            this.errorMessage = null;
        }
    }
}
