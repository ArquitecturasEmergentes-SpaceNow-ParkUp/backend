package pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.eventhandlers;

import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.events.PaymentCompletedEvent;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands.SendNotificationCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationChannel;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.valueobjects.NotificationType;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.services.NotificationCommandService;

/**
 * Event handler for Payment bounded context events
 * Listens to payment-related events and sends appropriate notifications
 */
@Component
public class PaymentEventHandlers {

    private final NotificationCommandService notificationCommandService;

    public PaymentEventHandlers(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Handles payment successful event
     * TODO: Implement @EventListener when event publishing is set up
     */
    @EventListener
    public void onPaymentSuccessful(PaymentCompletedEvent event) {
        Long userId = event.getUserId();
        Long paymentId = event.getPaymentId();
        Long reservationId = event.getReservationId();
        String amount = String.format("%.2f", event.getAmount());

        var command = new SendNotificationCommand(
                userId,
                NotificationChannel.WHATSAPP,
                NotificationType.PAYMENT_SUCCESS,
                "Payment Successful",
                "Your payment of $" + amount + " has been processed successfully.",
                "{\"paymentId\": " + paymentId + ", \"reservationId\": " + reservationId + ", \"amount\": \"$" + amount
                        + "\"}");
        notificationCommandService.handle(command);
    }

    /**
     * Handles payment failed event
     */
    @EventListener
    public void onPaymentFailed(Object event) {
        Long userId = 1L; // Get from event (no domain event modelled for failures yet)
        Long paymentId = 200L; // Get from event
        String reason = "Insufficient funds"; // Get from event

        var command = new SendNotificationCommand(
                userId,
                NotificationChannel.WHATSAPP,
                NotificationType.PAYMENT_FAILED,
                "Payment Failed",
                "Your payment failed: " + reason + ". Please update your payment method.",
                "{\"paymentId\": " + paymentId + ", \"reason\": \"" + reason + "\"}");
        notificationCommandService.handle(command);
    }

    /**
     * Handles refund processed event
     */
    @EventListener
    public void onRefundProcessed(Object event) {
        Long userId = 1L; // Get from event (no refund event modelled yet)
        Long refundId = 300L; // Get from event
        String amount = "$15.00"; // Get from event

        var command = new SendNotificationCommand(
                userId,
                NotificationChannel.WHATSAPP,
                NotificationType.PAYMENT_REFUND,
                "Refund Processed",
                "Your refund of " + amount
                        + " has been processed and will appear in your account within 5-7 business days.",
                "{\"refundId\": " + refundId + ", \"amount\": \"" + amount + "\"}");
        notificationCommandService.handle(command);
    }
}
