package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.aggregates.ExtraordinaryCase;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.valueobjects.CaseStatus;

import java.util.List;

@Repository
public interface ExtraordinaryCaseRepository extends JpaRepository<ExtraordinaryCase, Long> {

    // Útil para la "webpage" del Admin, para que vea solo los casos activos
    List<ExtraordinaryCase> findByStatus(CaseStatus status);
}