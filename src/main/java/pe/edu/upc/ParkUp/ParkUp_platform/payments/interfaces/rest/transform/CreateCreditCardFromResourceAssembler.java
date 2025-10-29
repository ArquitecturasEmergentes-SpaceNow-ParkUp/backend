package pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands.CreateCreditCardCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources.CreateCreditCardResource;

public class CreateCreditCardFromResourceAssembler {
    public static CreateCreditCardCommand toCommandFromResource(
            CreateCreditCardResource resource) {

        return new CreateCreditCardCommand(
                resource.cardNumber(), resource.cardHolderName(),resource.expirationDate(),resource.cvv(), resource.userId());
    }
}
