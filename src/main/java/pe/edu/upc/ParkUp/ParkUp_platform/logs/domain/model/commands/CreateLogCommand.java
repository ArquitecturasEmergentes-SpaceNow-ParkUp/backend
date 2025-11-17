package pe.edu.upc.ParkUp.ParkUp_platform.logs.domain.model.commands;

public record CreateLogCommand(
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
    String requestPayloadHash
) {}