package pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.entities.CreditCard;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources.CreditCardResource;



public class CreditCardResourceFromEntityAssembler {
    public static CreditCardResource toResourceFromEntity(CreditCard creditCard) {
        return new CreditCardResource(
                creditCard.getId(),
                creditCard.getCardNumber(),
                creditCard.getCardHolderName(),
                creditCard.getExpirationDate(),
                creditCard.getCvv(),
                creditCard.getUserId()
        );
    }
}

