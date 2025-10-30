package pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources.CreatePaymentResource;


public class CreatePaymentFromResourceAssembler {
    public static CreatePaymentCommand toCommandFromResource(CreatePaymentResource resource) {
        return new CreatePaymentCommand(resource.creditCardId(), resource.amount(), resource.reservationId());
    }
}
