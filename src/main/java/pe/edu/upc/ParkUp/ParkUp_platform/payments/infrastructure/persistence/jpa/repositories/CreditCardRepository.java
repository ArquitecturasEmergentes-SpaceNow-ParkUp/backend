package pe.edu.upc.ParkUp.ParkUp_platform.payments.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.entities.CreditCard;

import java.util.List;


public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findAllByUser_Id(Long userId);
}