package pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.aggregates.Payment;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.queries.GetAllPaymentsByUserIdQuery;

import java.util.List;



public interface PaymentQueryService {
    List<Payment> handle(GetAllPaymentsByUserIdQuery query);
}
