package pe.edu.upc.ParkUp.ParkUp_platform.notification.application.internal.eventhandlers;

import org.springframework.stereotype.Component;
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
    // @EventListener
    public void onPaymentSuccessful(Object event) {
        Long userId = 1L; // Get from event
        Long paymentId = 200L; // Get from event
        String amount = "$15.00"; // Get from event
        
        var command = new SendNotificationCommand(
                userId,
                NotificationChannel.PUSH,
                NotificationType.PAYMENT_SUCCESS,
                "Payment Successful",
                "Your payment of " + amount + " has been processed successfully.",
                "{\"paymentId\": " + paymentId + ", \"amount\": \"" + amount + "\"}"
        );
        notificationCommandService.handle(command);
    }

    /**
     * Handles payment failed event
     */
    // @EventListener
    public void onPaymentFailed(Object event) {
        Long userId = 1L; // Get from event
        Long paymentId = 200L; // Get from event
        String reason = "Insufficient funds"; // Get from event
        
        var command = new SendNotificationCommand(
                userId,
                NotificationChannel.PUSH,
                NotificationType.PAYMENT_FAILED,
                "Payment Failed",
                "Your payment failed: " + reason + ". Please update your payment method.",
                "{\"paymentId\": " + paymentId + ", \"reason\": \"" + reason + "\"}"
        );
        notificationCommandService.handle(command);
    }

    /**
     * Handles refund processed event
     */
    // @EventListener
    public void onRefundProcessed(Object event) {
        Long userId = 1L; // Get from event
        Long refundId = 300L; // Get from event
        String amount = "$15.00"; // Get from event
        
        var command = new SendNotificationCommand(
                userId,
                NotificationChannel.EMAIL,
                NotificationType.PAYMENT_REFUND,
                "Refund Processed",
                "Your refund of " + amount + " has been processed and will appear in your account within 5-7 business days.",
                "{\"refundId\": " + refundId + ", \"amount\": \"" + amount + "\"}"
        );
        notificationCommandService.handle(command);
    }
}
