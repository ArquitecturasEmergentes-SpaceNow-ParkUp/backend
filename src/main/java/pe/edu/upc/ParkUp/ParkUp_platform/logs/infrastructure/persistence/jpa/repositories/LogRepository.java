package pe.edu.upc.ParkUp.ParkUp_platform.logs.infrastructure.persistence.jpa.repositories;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.aggregates.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository

public interface LogRepository extends JpaRepository<Log, Long>, JpaSpecificationExecutor<Log> {
    
    // Basic filtering methods using Spring Data JPA naming conventions
    List<Log> findByActionContaining(String action);
    
    List<Log> findByUsernameContaining(String username);
    
    List<Log> findByStatus(String status);
    
    List<Log> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Log> findByActionContainingAndUsernameContaining(String action, String username);
    
    List<Log> findByActionContainingAndStatus(String action, String status);
    
    List<Log> findByUsernameContainingAndStatus(String username, String status);
    
    List<Log> findByActionContainingAndUsernameContainingAndStatus(String action, String username, String status);
    
    List<Log> findByActionContainingOrDetailsContainingOrUsernameContaining(String action, String details, String username);
    
    // Paging versions
    Page<Log> findByActionContaining(String action, Pageable pageable);
    
    Page<Log> findByUsernameContaining(String username, Pageable pageable);
    
    Page<Log> findByStatus(String status, Pageable pageable);
    
    Page<Log> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    Optional<Log> findById(Long id);
}