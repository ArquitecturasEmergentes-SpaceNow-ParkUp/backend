package pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.aggregates.Payment;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment payment) {
        return new PaymentResource(
                payment.getAmount(),
                payment.getCreditCardId() != null ? payment.getCreditCardId() : null
        );
    }
}
