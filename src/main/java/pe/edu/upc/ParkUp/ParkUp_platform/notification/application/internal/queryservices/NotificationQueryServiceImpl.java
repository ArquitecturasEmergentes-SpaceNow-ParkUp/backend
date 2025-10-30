package pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.aggregates.NotificationLog;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.entities.UserDevice;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.queries.*;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.services.NotificationQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.infrastructure.persistence.jpa.repositories.NotificationLogRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.infrastructure.persistence.jpa.repositories.UserDeviceRepository;

import java.util.List;

/**
 * Implementation of NotificationQueryService
 * Handles all read operations for notifications
 */
@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationLogRepository notificationLogRepository;
    private final UserDeviceRepository userDeviceRepository;

    public NotificationQueryServiceImpl(NotificationLogRepository notificationLogRepository,
                                       UserDeviceRepository userDeviceRepository) {
        this.notificationLogRepository = notificationLogRepository;
        this.userDeviceRepository = userDeviceRepository;
    }

    @Override
    public List<NotificationLog> handle(GetNotificationsByUserIdQuery query) {
        return notificationLogRepository.findByUserIdOrderByCreatedAtDesc(query.userId());
    }

    @Override
    public List<NotificationLog> handle(GetUnreadNotificationsByUserIdQuery query) {
        return notificationLogRepository.findUnreadNotificationsByUserId(query.userId());
    }

    @Override
    public List<UserDevice> handle(GetUserDevicesByUserIdQuery query) {
        return userDeviceRepository.findByUserId(query.userId());
    }

    @Override
    public List<UserDevice> handle(GetActiveDevicesByUserIdQuery query) {
        return userDeviceRepository.findByUserIdAndIsActive(query.userId(), true);
    }

    @Override
    public List<NotificationLog> handle(GetNotificationsByStatusQuery query) {
        return notificationLogRepository.findByStatus(query.status());
    }
}
