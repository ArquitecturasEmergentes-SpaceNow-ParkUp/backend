package pe.edu.upc.ParkUp.ParkUp_platform.payments.application.internal.commandservices;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.domain.model.aggregates.User;
import pe.edu.upc.ParkUp.ParkUp_platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands.CreateCreditCardCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.commands.DeleteCreditCardCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.entities.CreditCard;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services.CreditCardCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.infrastructure.persistence.jpa.repositories.CreditCardRepository;

@Service
public class CreditCardCommandServiceImpl implements CreditCardCommandService {

    private final CreditCardRepository creditCardRepository;
    private final UserRepository userRepository;

    public CreditCardCommandServiceImpl(CreditCardRepository creditCardRepository,
                                        UserRepository userRepository) {
        this.creditCardRepository = creditCardRepository;
        this.userRepository = userRepository;
    }


    @Override
    public CreditCard handle(CreateCreditCardCommand command) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        CreditCard card = new CreditCard();
        card.setExpirationDate(command.expirationDate());
        card.setCardNumber(command.cardNumber());
        card.setCvv(command.cvv());
        card.setCardHolderName(command.cardHolderName());
        card.setUser(user);

        return creditCardRepository.save(card);
    }


    @Override
    public void handle(DeleteCreditCardCommand command) {
        creditCardRepository.deleteById(command.creditCardId());
    }
}