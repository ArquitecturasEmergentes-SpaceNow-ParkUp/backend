package pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.outboundservices.EmailNotificationProvider;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.outboundservices.PushNotificationProvider;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.outboundservices.WhatsAppNotificationProvider;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.aggregates.NotificationLog;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands.*;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.entities.UserDevice;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.services.NotificationCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.infrastructure.persistence.jpa.repositories.NotificationLogRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.infrastructure.persistence.jpa.repositories.UserDeviceRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.acl.ProfileContextFacade;

import java.util.Optional;

/**
 * Implementation of NotificationCommandService
 * Handles all write operations for notifications with actual sending logic
 */
@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationLogRepository notificationLogRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final PushNotificationProvider pushProvider;
    private final EmailNotificationProvider emailProvider;
    private final WhatsAppNotificationProvider whatsAppProvider;
    private final ProfileContextFacade profileContextFacade;

    public NotificationCommandServiceImpl(NotificationLogRepository notificationLogRepository,
                                         UserDeviceRepository userDeviceRepository,
                                         PushNotificationProvider pushProvider,
                                         EmailNotificationProvider emailProvider,
                                         WhatsAppNotificationProvider whatsAppProvider,
                                         ProfileContextFacade profileContextFacade) {
        this.notificationLogRepository = notificationLogRepository;
        this.userDeviceRepository = userDeviceRepository;
        this.pushProvider = pushProvider;
        this.emailProvider = emailProvider;
        this.whatsAppProvider = whatsAppProvider;
        this.profileContextFacade = profileContextFacade;
    }

    @Override
    @Transactional
    public Optional<NotificationLog> handle(SendNotificationCommand command) {
        // Check if user has notifications enabled
        if (!profileContextFacade.areNotificationsEnabled(command.userId())) {
            System.out.println("⚠️ Notifications disabled for user " + command.userId());
            // Still create log but mark as skipped
            var notificationLog = new NotificationLog(
                    command.userId(),
                    command.channel(),
                    command.type(),
                    command.title(),
                    command.message(),
                    command.metadata()
            );
            notificationLog.markAsFailed("User has notifications disabled");
            return Optional.of(notificationLogRepository.save(notificationLog));
        }

        // Create notification log
        var notificationLog = new NotificationLog(
                command.userId(),
                command.channel(),
                command.type(),
                command.title(),
                command.message(),
                command.metadata()
        );

        // Save as PENDING first
        var savedLog = notificationLogRepository.save(notificationLog);

        // Attempt to send based on channel
        String externalId = null;
        try {
            externalId = switch (command.channel()) {
                case PUSH -> sendPushNotification(command.userId(), command.title(), command.message(), command.metadata());
                case EMAIL -> sendEmailNotification(command.userId(), command.title(), command.message());
                case WHATSAPP -> sendWhatsAppNotification(command.userId(), command.message());
            };

            if (externalId != null) {
                savedLog.markAsSent(externalId);
            } else {
                savedLog.markAsFailed("Failed to send notification via " + command.channel());
            }
        } catch (Exception e) {
            savedLog.markAsFailed("Error: " + e.getMessage());
        }

        // Save with updated status
        var finalLog = notificationLogRepository.save(savedLog);
        return Optional.of(finalLog);
    }

    @Override
    @Transactional
    public Optional<UserDevice> handle(RegisterDeviceCommand command) {
        // Check if device token already exists
        var existingDevice = userDeviceRepository.findByDeviceToken(command.deviceToken());
        
        if (existingDevice.isPresent()) {
            // Update existing device (reactivate if needed)
            var device = existingDevice.get();
            device.activate();
            return Optional.of(userDeviceRepository.save(device));
        }

        // Create new device
        var device = new UserDevice(
                command.userId(),
                command.deviceToken(),
                command.deviceType(),
                command.deviceName()
        );

        var savedDevice = userDeviceRepository.save(device);
        return Optional.of(savedDevice);
    }

    @Override
    @Transactional
    public Optional<NotificationLog> handle(MarkNotificationAsReadCommand command) {
        var notificationLog = notificationLogRepository.findById(command.notificationId())
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        // Verify user owns the notification
        if (!notificationLog.getUserId().equals(command.userId())) {
            throw new IllegalArgumentException("User does not own this notification");
        }

        notificationLog.markAsRead();
        var savedLog = notificationLogRepository.save(notificationLog);
        return Optional.of(savedLog);
    }

    @Override
    @Transactional
    public Optional<UserDevice> handle(DeactivateDeviceCommand command) {
        var device = userDeviceRepository.findById(command.deviceId())
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        // Verify user owns the device
        if (!device.getUserId().equals(command.userId())) {
            throw new IllegalArgumentException("User does not own this device");
        }

        device.deactivate();
        var savedDevice = userDeviceRepository.save(device);
        return Optional.of(savedDevice);
    }

    // Private helper methods

    private String sendPushNotification(Long userId, String title, String message, String metadata) {
        // Find active devices for user
        var devices = userDeviceRepository.findByUserIdAndIsActive(userId, true);
        
        if (devices.isEmpty()) {
            System.out.println("No active devices found for user " + userId);
            return null;
        }

        // Send to first active device (could be extended to send to all)
        var device = devices.get(0);
        return pushProvider.sendPushNotification(device.getDeviceToken(), title, message, metadata);
    }

    private String sendEmailNotification(Long userId, String subject, String message) {
        // Check if user has email notifications enabled
        if (!profileContextFacade.areEmailNotificationsEnabled(userId)) {
            System.out.println("⚠️ Email notifications disabled for user " + userId);
            return null;
        }

        // TODO: Get user email from IAM BC (via ACL or shared service)
        String userEmail = "user" + userId + "@parkup.com"; // Mock email - should be fetched from IAM context
        
        return emailProvider.sendEmail(userEmail, subject, message, message);
    }

    private String sendWhatsAppNotification(Long userId, String message) {
        // Check if user has SMS/WhatsApp notifications enabled
        if (!profileContextFacade.areSmsNotificationsEnabled(userId)) {
            System.out.println("⚠️ SMS/WhatsApp notifications disabled for user " + userId);
            return null;
        }

        // Get user phone number from profile
        var phoneNumberOpt = profileContextFacade.getUserPhoneNumber(userId);
        
        if (phoneNumberOpt.isEmpty()) {
            System.err.println("❌ No phone number found for user " + userId);
            return null;
        }

        String phoneNumber = phoneNumberOpt.get();
        System.out.println("📱 Sending WhatsApp to: " + phoneNumber);
        
        return whatsAppProvider.sendWhatsApp(phoneNumber, message);
    }
}
