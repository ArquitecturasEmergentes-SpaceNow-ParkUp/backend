package pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event published when a payment is successfully completed
 */
@Getter
public class PaymentCompletedEvent extends ApplicationEvent {
    private final Long paymentId;
    private final Long reservationId;
    private final Long userId;
    private final Double amount;

    public PaymentCompletedEvent(Object source, Long paymentId, Long reservationId, Long userId, Double amount) {
        super(source);
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.userId = userId;
        this.amount = amount;
    }
}
