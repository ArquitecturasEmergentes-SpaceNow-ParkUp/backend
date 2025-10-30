package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.aggregates.NotificationLog;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands.*;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.entities.UserDevice;

import java.util.Optional;

/**
 * Notification command service interface
 * Handles all write operations for notifications
 */
public interface NotificationCommandService {
    
    /**
     * Sends a notification to a user
     *
     * @param command The send notification command
     * @return The created notification log
     */
    Optional<NotificationLog> handle(SendNotificationCommand command);
    
    /**
     * Registers a user device for push notifications
     *
     * @param command The register device command
     * @return The registered device
     */
    Optional<UserDevice> handle(RegisterDeviceCommand command);
    
    /**
     * Marks a notification as read
     *
     * @param command The mark as read command
     * @return The updated notification log
     */
    Optional<NotificationLog> handle(MarkNotificationAsReadCommand command);
    
    /**
     * Deactivates a user device
     *
     * @param command The deactivate device command
     * @return The deactivated device
     */
    Optional<UserDevice> handle(DeactivateDeviceCommand command);
}
