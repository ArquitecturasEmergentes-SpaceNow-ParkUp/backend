package pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.queries;

import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationStatus;

/**
 * Query to get pending notifications (for retry mechanism)
 */
public record GetNotificationsByStatusQuery(NotificationStatus status) {
}
