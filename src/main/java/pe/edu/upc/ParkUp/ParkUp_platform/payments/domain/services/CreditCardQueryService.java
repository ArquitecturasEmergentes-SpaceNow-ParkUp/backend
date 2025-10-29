package pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.entities.CreditCard;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.queries.GetAllCreditCardsByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.queries.GetCreditCardByIdQuery;

import java.util.List;


public interface CreditCardQueryService {
    List<CreditCard> handle(GetAllCreditCardsByUserIdQuery query);
    CreditCard handle(GetCreditCardByIdQuery query);
}

