package pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands.DeleteCreditCardCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources.DeleteCreditCardResource;

public class DeleteCreditCardFromResourceAssembler {
    public static DeleteCreditCardCommand toCommandFromResource(DeleteCreditCardResource resource) {
        return new DeleteCreditCardCommand(resource.creditCardId());
    }
}
