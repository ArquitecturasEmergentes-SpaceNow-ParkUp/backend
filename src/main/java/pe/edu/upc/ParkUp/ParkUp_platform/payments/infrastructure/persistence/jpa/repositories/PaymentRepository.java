package pe.edu.upc.ParkUp.ParkUp_platform.payments.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.aggregates.Payment;

import java.util.List;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByCreditCard_User_Id(Long userId);
}