package pe.edu.upc.ParkUp.ParkUp_platform.reservation.application.internal.eventhandlers;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.events.PaymentCompletedEvent;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands.ConfirmPaymentCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.services.ReservationCommandService;

/**
 * Event handler that listens for payment completion events
 * and updates the reservation status accordingly
 */
@Component
public class PaymentEventHandler {

    private final ReservationCommandService reservationCommandService;

    public PaymentEventHandler(ReservationCommandService reservationCommandService) {
        this.reservationCommandService = reservationCommandService;
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        System.out.println("Payment completed event received for reservation: " + event.getReservationId());
        
        try {
            var command = new ConfirmPaymentCommand(event.getReservationId());
            reservationCommandService.handle(command);
        } catch (Exception e) {
            System.err.println("Error handling payment completed event: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
