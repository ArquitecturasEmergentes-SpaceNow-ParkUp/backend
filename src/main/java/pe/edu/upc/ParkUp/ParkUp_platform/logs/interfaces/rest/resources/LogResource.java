package pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.resources;

import java.time.LocalDateTime;

public record LogResource(
    Long id,
    LocalDateTime timestamp,
    String action,
    Long userId,
    String username,
    String userEmail,
    String details,
    String ipAddress,
    String userAgent,
    String resourceType,
    Long resourceId,
    String status,
    String errorMessage,
    Long executionTimeMs
    , String requestPayloadHash
) {}