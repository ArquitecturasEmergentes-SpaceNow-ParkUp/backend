package pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.aggregates.Payment;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands.CreatePaymentCommand;


public interface PaymentCommandService {
    Payment handle(CreatePaymentCommand command);
}