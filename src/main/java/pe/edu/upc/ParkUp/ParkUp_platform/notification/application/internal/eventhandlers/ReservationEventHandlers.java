package pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.eventhandlers;

import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.events.ReservationConfirmedEvent;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.events.ReservationCreatedEvent;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.events.ReservationCancelledEvent;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.events.ReservationStartedEvent;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.events.ReservationCompletedEvent;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands.SendNotificationCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationChannel;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationType;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.services.NotificationCommandService;

/**
 * Event handler for Reservation bounded context events
 * Listens to reservation-related events and sends appropriate notifications
 */
@Component
public class ReservationEventHandlers {

    private final NotificationCommandService notificationCommandService;

    public ReservationEventHandlers(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Handles reservation confirmed event
     * TODO: Implement @EventListener when event publishing is set up
     */
    @EventListener
    public void on(ReservationConfirmedEvent reservationConfirmedEvent) {
        // Extract data from event
        Long userId = reservationConfirmedEvent.getUserId();
        Long reservationId = reservationConfirmedEvent.getReservationId();
        
        // Send PUSH notification
        var pushCommand = new SendNotificationCommand(
                userId,
                NotificationChannel.PUSH,
                NotificationType.RESERVATION_CONFIRMED,
                "Reservation Confirmed",
                "Your parking reservation #" + reservationId + " has been confirmed!",
                "{\"reservationId\": " + reservationId + "}"
        );
        notificationCommandService.handle(pushCommand);
        
        // Send EMAIL notification
        var emailCommand = new SendNotificationCommand(
                userId,
                NotificationChannel.EMAIL,
                NotificationType.RESERVATION_CONFIRMED,
                "Reservation Confirmed - ParkUp",
                "Your parking reservation #" + reservationId + " has been confirmed. You can view details in the app.",
                "{\"reservationId\": " + reservationId + "}"
        );
        notificationCommandService.handle(emailCommand);
    }

    /**
     * Handles reservation reminder event
     * TODO: Implement @EventListener when event publishing is set up
     */
    @EventListener
    public void onReservationReminder(ReservationCreatedEvent event) {
        Long userId = event.getUserId();
        Long reservationId = event.getReservationId();
        var startTime = event.getStartTime();
        String startStr = (startTime != null) ? startTime.toString() : "soon";

        var command = new SendNotificationCommand(
                userId,
                NotificationChannel.PUSH,
                NotificationType.RESERVATION_REMINDER,
                "Reservation Starting Soon",
                "Your parking reservation starts at " + startStr + ". Don't be late!",
                "{\"reservationId\": " + reservationId + "}"
        );
        notificationCommandService.handle(command);
    }

    /**
     * Handles reservation cancelled event
     */
    @EventListener
    public void onReservationCancelled(ReservationCancelledEvent event) {
        Long userId = event.getUserId();
        Long reservationId = event.getReservationId();
        
        var command = new SendNotificationCommand(
                userId,
                NotificationChannel.PUSH,
                NotificationType.RESERVATION_CANCELLED,
                "Reservation Cancelled",
                "Your parking reservation #" + reservationId + " has been cancelled.",
                "{\"reservationId\": " + reservationId + "}"
        );
        notificationCommandService.handle(command);
    }

    /**
     * Handles reservation started event
     */
    @EventListener
    public void onReservationStarted(ReservationStartedEvent event) {
        Long userId = event.getUserId();
        Long reservationId = event.getReservationId();
        
        var command = new SendNotificationCommand(
                userId,
                NotificationChannel.PUSH,
                NotificationType.RESERVATION_STARTED,
                "Parking Session Started",
                "Your parking session has started. Enjoy your stay!",
                "{\"reservationId\": " + reservationId + "}"
        );
        notificationCommandService.handle(command);
    }

    /**
     * Handles reservation completed event
     */
    @EventListener
    public void onReservationCompleted(ReservationCompletedEvent event) {
        Long userId = event.getUserId();
        Long reservationId = event.getReservationId();
        
        var command = new SendNotificationCommand(
                userId,
                NotificationChannel.PUSH,
                NotificationType.RESERVATION_COMPLETED,
                "Parking Session Completed",
                "Your parking session is complete. Thank you for using ParkUp!",
                "{\"reservationId\": " + reservationId + "}"
        );
        notificationCommandService.handle(command);
    }

    /**
     * Handles reservation expired event
     */
    @EventListener
    public void onReservationExpired(Object event) {
        // No explicit ReservationExpiredEvent defined in domain; keep as generic listener for now
        Long userId = 1L; // Keep placeholder until an event is modeled
        Long reservationId = 100L; // Keep placeholder until an event is modeled
        
        var command = new SendNotificationCommand(
                userId,
                NotificationChannel.PUSH,
                NotificationType.RESERVATION_EXPIRED,
                "Parking Time Exceeded",
                "Your parking time has expired. Additional charges may apply.",
                "{\"reservationId\": " + reservationId + "}"
        );
        notificationCommandService.handle(command);
    }
}
