package pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.aggregates.Log;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.queries.GetAllLogsQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.queries.GetLogByIdQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LogQueryService {
    Optional<Log> handle(GetLogByIdQuery query);
    Page<Log> handle(GetAllLogsQuery query, Pageable pageable);
}