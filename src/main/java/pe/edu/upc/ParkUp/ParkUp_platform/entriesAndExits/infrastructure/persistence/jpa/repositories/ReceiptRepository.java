package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.Receipt;

import java.util.Optional;

/**
 * This interface is responsible for providing the Receipt entity related operations.
 * It extends the JpaRepository interface.
 */
@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    /**
     * This method is responsible for finding the receipt by payment ID.
     * @param paymentId The payment ID.
     * @return The receipt object.
     */
    Optional<Receipt> findByPaymentId(Long paymentId);

    /**
     * This method is responsible for checking if the receipt exists by payment ID.
     * @param paymentId The payment ID.
     * @return True if the receipt exists, false otherwise.
     */
    boolean existsByPaymentId(Long paymentId);
}
