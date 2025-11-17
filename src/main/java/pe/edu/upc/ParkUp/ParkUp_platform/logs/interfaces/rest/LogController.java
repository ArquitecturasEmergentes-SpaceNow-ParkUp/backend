package pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.aggregates.Log;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.queries.GetAllLogsQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.queries.GetLogByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.resources.CreateLogResource;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.resources.LogResource;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.transform.CreateLogCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.transform.LogResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "Logs", description = "Admin Logs Management Endpoints")
public class LogController {
    
    private final LogCommandService logCommandService;
    private final LogQueryService logQueryService;
    
    public LogController(LogCommandService logCommandService, LogQueryService logQueryService) {
        this.logCommandService = logCommandService;
        this.logQueryService = logQueryService;
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<LogResource>> getAllLogs(
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable,
            HttpServletRequest request) {
        
        GetAllLogsQuery query = new GetAllLogsQuery(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            action,
            username,
            status,
            startDate,
            endDate,
            search
        );
        
        Pageable effectivePageable = sanitizeSort(pageable, request);
        Page<Log> logs = logQueryService.handle(query, effectivePageable);
        Page<LogResource> logResources = logs.map(LogResourceFromEntityAssembler::toResourceFromEntity);
        
        return ResponseEntity.ok(logResources);
    }

    private Pageable sanitizeSort(Pageable pageable, HttpServletRequest request) {
        String[] sortParams = request.getParameterValues("sort");
        if (sortParams == null || sortParams.length == 0) return pageable;

        Sort sort = Sort.unsorted();
        for (String sortParam : sortParams) {
            if (sortParam == null) continue;

            String normalized = sortParam.trim();

            // If param is a JSON array such as ["string"] or ["field","desc"]
            if (normalized.startsWith("[") && normalized.endsWith("]")) {
                normalized = normalized.substring(1, normalized.length() - 1).trim();
                normalized = normalized.replaceAll("\"", ""); // remove double quotes
            }

            if (normalized.isEmpty()) continue;

            String[] parts = normalized.split(",");
            String property = parts[0].trim();
            // ignore unknown/unmapped properties to prevent Spring Data exceptions
            if (!isAllowedSortProperty(property)) continue;
            if (property.isEmpty()) continue;
            Sort.Direction direction = Sort.Direction.ASC;
            if (parts.length > 1) {
                try {
                    direction = Sort.Direction.fromString(parts[1].trim());
                } catch (Exception ignored) {
                }
            }

            sort = sort.and(Sort.by(direction, property));
        }

        if (sort.isUnsorted()) return pageable;
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    private boolean isAllowedSortProperty(String prop) {
        if (prop == null || prop.isBlank()) return false;
        String p = prop.toLowerCase();
        // Allowed sort properties for Log entity
        return switch (p) {
            case "id", "timestamp", "action", "username", "useremail", "user_email", "user_id", "status", "resource_type" -> true;
            default -> false;
        };
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LogResource> getLogById(@PathVariable Long id) {
        GetLogByIdQuery query = new GetLogByIdQuery(id);
        
        return logQueryService.handle(query)
                .map(LogResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LogResource> createLog(@RequestBody CreateLogResource resource) {
        CreateLogCommand command = CreateLogCommandFromResourceAssembler.toCommandFromResource(resource);
        Log log = logCommandService.handle(command);
        LogResource logResource = LogResourceFromEntityAssembler.toResourceFromEntity(log);
        
        return new ResponseEntity<>(logResource, HttpStatus.CREATED);
    }
}