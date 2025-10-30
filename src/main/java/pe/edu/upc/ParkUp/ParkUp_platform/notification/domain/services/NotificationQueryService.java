package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.aggregates.NotificationLog;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.entities.UserDevice;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.queries.*;

import java.util.List;

/**
 * Notification query service interface
 * Handles all read operations for notifications
 */
public interface NotificationQueryService {
    
    /**
     * Gets all notifications for a user
     *
     * @param query The get notifications by user ID query
     * @return List of notification logs
     */
    List<NotificationLog> handle(GetNotificationsByUserIdQuery query);
    
    /**
     * Gets unread notifications for a user
     *
     * @param query The get unread notifications query
     * @return List of unread notification logs
     */
    List<NotificationLog> handle(GetUnreadNotificationsByUserIdQuery query);
    
    /**
     * Gets user devices
     *
     * @param query The get user devices query
     * @return List of user devices
     */
    List<UserDevice> handle(GetUserDevicesByUserIdQuery query);
    
    /**
     * Gets active devices for a user
     *
     * @param query The get active devices query
     * @return List of active devices
     */
    List<UserDevice> handle(GetActiveDevicesByUserIdQuery query);
    
    /**
     * Gets notifications by status
     *
     * @param query The get notifications by status query
     * @return List of notifications with the specified status
     */
    List<NotificationLog> handle(GetNotificationsByStatusQuery query);
}
