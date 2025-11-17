package pe.edu.upc.ParkUp.ParkUp_platform.logs.application.internal.queryservices;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.aggregates.Log;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.queries.GetAllLogsQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.queries.GetLogByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.infrastructure.persistence.jpa.repositories.LogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;

@Service
public class LogQueryServiceImpl implements LogQueryService {
    
    private final LogRepository logRepository;
    
    public LogQueryServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    
    @Override
    public Optional<Log> handle(GetLogByIdQuery query) {
        return logRepository.findById(query.id());
    }
    
    @Override
    public Page<Log> handle(GetAllLogsQuery query, Pageable pageable) {
        // Build dynamic specification to push filters to the database
        Specification<Log> spec = Specification.where(null);

        if (query.action() != null && !query.action().isEmpty()) {
            String actionLower = query.action().toLowerCase();
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.get("action")), "%" + actionLower + "%"));
        }

        if (query.username() != null && !query.username().isEmpty()) {
            String usernameLower = query.username().toLowerCase();
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.get("username")), "%" + usernameLower + "%"));
        }

        if (query.status() != null && !query.status().isEmpty()) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("status"), query.status()));
        }
        
        // Applied other filters via database specification
        
        // Handle date range filtering
        if (query.startDate() != null && !query.startDate().isEmpty()) {
            try {
                LocalDateTime startDate = LocalDateTime.parse(query.startDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                spec = spec.and((root, q, cb) -> cb.greaterThanOrEqualTo(root.get("timestamp"), startDate));
            } catch (Exception e) {
                // Handle parsing error - ignore this filter
            }
        }
        
        if (query.endDate() != null && !query.endDate().isEmpty()) {
            try {
                LocalDateTime endDate = LocalDateTime.parse(query.endDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                spec = spec.and((root, q, cb) -> cb.lessThanOrEqualTo(root.get("timestamp"), endDate));
            } catch (Exception e) {
                // Handle parsing error - ignore this filter
            }
        }
        
        // Handle search across multiple fields
        if (query.search() != null && !query.search().isEmpty()) {
            String searchLower = query.search().toLowerCase();
            spec = spec.and((root, q, cb) -> cb.or(
                    cb.like(cb.lower(root.get("action")), "%" + searchLower + "%"),
                    cb.like(cb.lower(root.get("details")), "%" + searchLower + "%"),
                    cb.like(cb.lower(root.get("username")), "%" + searchLower + "%")
            ));
        }

        Page<Log> result = logRepository.findAll(spec, pageable);
        return result;
    }
}