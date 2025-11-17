package pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.aggregates.Log;
import pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.resources.LogResource;

public class LogResourceFromEntityAssembler {
    
    public static LogResource toResourceFromEntity(Log log) {
        return new LogResource(
            log.getId(),
            log.getTimestamp(),
            log.getAction(),
            log.getUserId(),
            log.getUsername(),
            log.getUserEmail(),
            log.getDetails(),
            log.getIpAddress(),
            log.getUserAgent(),
            log.getResourceType(),
            log.getResourceId(),
            log.getStatus(),
            log.getErrorMessage(),
            log.getExecutionTimeMs()
            , log.getRequestPayloadHash()
        );
    }
}