package pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands.CreateCreditCardCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands.DeleteCreditCardCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.entities.CreditCard;

public interface CreditCardCommandService {
    CreditCard handle(CreateCreditCardCommand command);
    void handle(DeleteCreditCardCommand command);
}
