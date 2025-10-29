package pe.edu.upc.ParkUp.ParkUp_platform.payments.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.aggregates.Payment;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.queries.GetAllPaymentsByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services.PaymentQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.infrastructure.persistence.jpa.repositories.PaymentRepository;

import java.util.List;


@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> handle(GetAllPaymentsByUserIdQuery query) {
        return paymentRepository.findAllByCreditCard_User_Id(query.userId());
    }
}

