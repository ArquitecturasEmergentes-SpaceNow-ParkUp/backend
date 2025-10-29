package pe.edu.upc.ParkUp.ParkUp_platform.payments.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.aggregates.Payment;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.entities.CreditCard;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services.PaymentCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.infrastructure.persistence.jpa.repositories.CreditCardRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.infrastructure.persistence.jpa.repositories.PaymentRepository;


@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final CreditCardRepository creditCardRepository;

    public PaymentCommandServiceImpl(PaymentRepository paymentRepository, CreditCardRepository creditCardRepository) {
        this.paymentRepository = paymentRepository;
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public Payment handle(CreatePaymentCommand command) {
        CreditCard card = creditCardRepository.findById(command.creditCardId())
                .orElseThrow(() -> new IllegalArgumentException("Credit card not found"));
        Payment payment = new Payment(command.amount(), card);
        return paymentRepository.save(payment);
    }
}

