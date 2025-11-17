package pe.edu.upc.ParkUp.ParkUp_platform.logs.application.internal.commandservices;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.aggregates.Log;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands.CreateLogCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.services.LogCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.infrastructure.persistence.jpa.repositories.LogRepository;
import org.springframework.stereotype.Service;

@Service
public class LogCommandServiceImpl implements LogCommandService {
    
    private final LogRepository logRepository;
    
    public LogCommandServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    
    @Override
    public Log handle(CreateLogCommand command) {
        Log log = new Log(
            command.action(),
            command.userId(),
            command.username(),
            command.userEmail(),
            command.details(),
            command.ipAddress(),
            command.userAgent(),
            command.resourceType(),
            command.resourceId(),
            command.status()
        );

        // Optional: set payload hash if present
        if (command.requestPayloadHash() != null) {
            log.setRequestPayloadHash(command.requestPayloadHash());
        }
        
        return logRepository.save(log);
    }
}