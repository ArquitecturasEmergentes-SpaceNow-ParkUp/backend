package pe.edu.upc.ParkUp.ParkUp_platform.notification.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.aggregates.NotificationLog;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationStatus;

import java.util.List;

/**
 * Notification log repository interface
 * Provides data access methods for NotificationLog aggregate
 */
@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
    
    /**
     * Finds all notifications for a specific user
     *
     * @param userId The user ID
     * @return List of notification logs ordered by creation date (newest first)
     */
    List<NotificationLog> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    /**
     * Finds unread notifications for a user
     *
     * @param userId The user ID
     * @return List of unread notifications
     */
    @Query("SELECT n FROM NotificationLog n WHERE n.userId = :userId AND n.status IN ('SENT', 'PENDING') ORDER BY n.createdAt DESC")
    List<NotificationLog> findUnreadNotificationsByUserId(@Param("userId") Long userId);
    
    /**
     * Finds notifications by status
     *
     * @param status The notification status
     * @return List of notifications with the specified status
     */
    List<NotificationLog> findByStatus(NotificationStatus status);
    
    /**
     * Counts unread notifications for a user
     *
     * @param userId The user ID
     * @return Count of unread notifications
     */
    @Query("SELECT COUNT(n) FROM NotificationLog n WHERE n.userId = :userId AND n.status IN ('SENT', 'PENDING')")
    Long countUnreadNotificationsByUserId(@Param("userId") Long userId);
    
    /**
     * Finds failed notifications to retry
     *
     * @return List of failed notifications
     */
    @Query("SELECT n FROM NotificationLog n WHERE n.status = 'FAILED' ORDER BY n.createdAt ASC")
    List<NotificationLog> findFailedNotificationsForRetry();
}
