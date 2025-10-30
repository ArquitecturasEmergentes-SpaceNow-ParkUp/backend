package pe.edu.upc.ParkUp.ParkUp_platform.reservation.application.internal.outboundservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands.SendNotificationCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationChannel;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationType;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.services.NotificationCommandService;

/**
 * External service for notifications bounded context communication
 * This service integrates with the Notifications BC to send notifications
 */
@Service
public class NotificationService {

    private final NotificationCommandService notificationCommandService;

    public NotificationService(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Sends a WhatsApp notification when a reservation is confirmed
     *
     * @param userId        The user ID
     * @param reservationId The reservation ID
     * @return true if notification was sent successfully
     */
    public boolean sendReservationConfirmedNotification(Long userId, Long reservationId) {
        try {
            var command = new SendNotificationCommand(
                    userId,
                    NotificationChannel.WHATSAPP,
                    NotificationType.RESERVATION_CONFIRMED,
                    "Reserva Confirmada",
                    "Tu reserva con ID #" + reservationId + " ha sido confirmada exitosamente. " +
                    "El pago ha sido procesado correctamente. ¡Te esperamos!",
                    "{\"reservationId\": " + reservationId + "}"
            );
            
            notificationCommandService.handle(command);
            System.out.println("WhatsApp notification sent: Reservation " + reservationId + " confirmed for user " + userId);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to send notification: " + e.getMessage());
            return false;
        }
    }

    /**
     * Sends a reminder notification before reservation start time
     *
     * @param userId        The user ID
     * @param reservationId The reservation ID
     * @return true if notification was sent successfully
     */
    public boolean sendReservationReminderNotification(Long userId, Long reservationId) {
        try {
            var command = new SendNotificationCommand(
                    userId,
                    NotificationChannel.WHATSAPP,
                    NotificationType.RESERVATION_REMINDER,
                    "Recordatorio de Reserva",
                    "¡Recordatorio! Tu reserva #" + reservationId + " comenzará pronto. No olvides llegar a tiempo.",
                    "{\"reservationId\": " + reservationId + "}"
            );
            
            notificationCommandService.handle(command);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to send reminder: " + e.getMessage());
            return false;
        }
    }

    /**
     * Sends a notification when a reservation is cancelled
     *
     * @param userId        The user ID
     * @param reservationId The reservation ID
     * @return true if notification was sent successfully
     */
    public boolean sendReservationCancelledNotification(Long userId, Long reservationId) {
        try {
            var command = new SendNotificationCommand(
                    userId,
                    NotificationChannel.WHATSAPP,
                    NotificationType.RESERVATION_CANCELLED,
                    "Reserva Cancelada",
                    "Tu reserva #" + reservationId + " ha sido cancelada.",
                    "{\"reservationId\": " + reservationId + "}"
            );
            
            notificationCommandService.handle(command);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to send cancellation notification: " + e.getMessage());
            return false;
        }
    }

    /**
     * Sends a notification when a reservation has expired
     *
     * @param userId        The user ID
     * @param reservationId The reservation ID
     * @return true if notification was sent successfully
     */
    public boolean sendReservationExpiredNotification(Long userId, Long reservationId) {
        try {
            var command = new SendNotificationCommand(
                    userId,
                    NotificationChannel.WHATSAPP,
                    NotificationType.RESERVATION_EXPIRED,
                    "Reserva Expirada",
                    "Tu reserva #" + reservationId + " ha expirado.",
                    "{\"reservationId\": " + reservationId + "}"
            );
            
            notificationCommandService.handle(command);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to send expiration notification: " + e.getMessage());
            return false;
        }
    }
}
