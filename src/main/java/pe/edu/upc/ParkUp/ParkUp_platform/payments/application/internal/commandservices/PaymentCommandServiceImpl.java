package pe.edu.upc.ParkUp.ParkUp_platform.payments.application.internal.commandservices;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.aggregates.Payment;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.entities.CreditCard;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.events.PaymentCompletedEvent;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services.PaymentCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.infrastructure.persistence.jpa.repositories.CreditCardRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.infrastructure.persistence.jpa.repositories.PaymentRepository;


@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final CreditCardRepository creditCardRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PaymentCommandServiceImpl(PaymentRepository paymentRepository, 
                                    CreditCardRepository creditCardRepository,
                                    ApplicationEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.creditCardRepository = creditCardRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Payment handle(CreatePaymentCommand command) {
        CreditCard card = creditCardRepository.findById(command.creditCardId())
                .orElseThrow(() -> new IllegalArgumentException("Credit card not found"));
        Payment payment = new Payment(command.amount(), card, command.reservationId());
        Payment savedPayment = paymentRepository.save(payment);
        
        // Publish event for reservation confirmation
        eventPublisher.publishEvent(new PaymentCompletedEvent(
            this, 
            savedPayment.getId(), 
            savedPayment.getReservationId(), 
            savedPayment.getUserId(),
            savedPayment.getAmount()
        ));
        
        return savedPayment;
    }
}

