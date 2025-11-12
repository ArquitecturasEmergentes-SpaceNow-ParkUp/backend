package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.aggregates.ExtraordinaryCase;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.queries.GetAllExtraordinaryCasesQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.queries.GetExtraordinaryCaseByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ExtraordinaryCaseQueryService {
    List<ExtraordinaryCase> handle(GetAllExtraordinaryCasesQuery query);
    Optional<ExtraordinaryCase> handle(GetExtraordinaryCaseByIdQuery query);
}
