package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.application.internal.queryservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.aggregates.ExtraordinaryCase;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.queries.GetAllExtraordinaryCasesQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.queries.GetExtraordinaryCaseByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.services.ExtraordinaryCaseQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.infrastructure.persistence.jpa.repositories.ExtraordinaryCaseRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExtraordinaryCaseQueryServiceImpl implements ExtraordinaryCaseQueryService {

    private final ExtraordinaryCaseRepository extraordinaryCaseRepository;

    @Override
    public List<ExtraordinaryCase> handle(GetAllExtraordinaryCasesQuery query) {
        return extraordinaryCaseRepository.findAll();
    }

    @Override
    public Optional<ExtraordinaryCase> handle(GetExtraordinaryCaseByIdQuery query) {
        return extraordinaryCaseRepository.findById(query.extraordinaryCaseId());
    }
}