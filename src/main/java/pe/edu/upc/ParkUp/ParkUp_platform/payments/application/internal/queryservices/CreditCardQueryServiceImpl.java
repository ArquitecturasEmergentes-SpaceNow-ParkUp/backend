package pe.edu.upc.ParkUp.ParkUp_platform.payments.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.entities.CreditCard;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.queries.GetAllCreditCardsByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.queries.GetCreditCardByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services.CreditCardQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.infrastructure.persistence.jpa.repositories.CreditCardRepository;

import java.util.List;

@Service
public class CreditCardQueryServiceImpl implements CreditCardQueryService {

    private final CreditCardRepository creditCardRepository;

    public CreditCardQueryServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public List<CreditCard> handle(GetAllCreditCardsByUserIdQuery query) {
        return creditCardRepository.findAllByUser_Id(query.userId());
    }

    @Override
    public CreditCard handle(GetCreditCardByIdQuery query) {
        return creditCardRepository.findById(query.creditCardId())
                .orElseThrow(() -> new IllegalArgumentException("Credit card not found"));
    }
}
