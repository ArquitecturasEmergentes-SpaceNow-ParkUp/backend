package pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateLogResource(
    @Schema(description = "The action name (e.g. LOGIN, RESERVATION_CREATED)") String action,
    @Schema(description = "The id of the acting user") Long userId,
    @Schema(description = "Username of the acting user") String username,
    @Schema(description = "User email of the acting user") String userEmail,
    @Schema(description = "Detailed metadata about the action (JSON string, optional)") String details,
    @Schema(description = "IP address that caused the action") String ipAddress,
    @Schema(description = "User-Agent HTTP header value") String userAgent,
    @Schema(description = "The resource type affected (e.g., RESERVATION, PROFILE)") String resourceType,
    @Schema(description = "The resource id affected (e.g., reservation id)") Long resourceId,
    @Schema(description = "Action status: SUCCESS, FAILURE, WARNING") String status,
    @Schema(description = "Optional request payload content hashed (SHA256 or similar)") String requestPayloadHash
) {}