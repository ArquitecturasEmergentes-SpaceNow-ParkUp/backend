package pe.edu.upc.ParkUp.ParkUp_platform.logs.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record LogResource(
    @Schema(description = "Log entry id") Long id,
    @Schema(description = "Timestamp of the logged event") LocalDateTime timestamp,
    @Schema(description = "Action name/op") String action,
    @Schema(description = "Acting user id (nullable)") Long userId,
    @Schema(description = "Acting user's username") String username,
    @Schema(description = "Acting user's email") String userEmail,
    @Schema(description = "Details (large text) about the action") String details,
    @Schema(description = "Source IP address") String ipAddress,
    @Schema(description = "User-Agent header content") String userAgent,
    @Schema(description = "Affected resource type (optional)") String resourceType,
    @Schema(description = "Affected resource id (optional)") Long resourceId,
    @Schema(description = "Status of the action: SUCCESS/FAILURE/WARNING") String status,
    @Schema(description = "Optional error message when status=FAILURE") String errorMessage,
    @Schema(description = "Execution time in ms") Long executionTimeMs,
    @Schema(description = "Optional request payload hash") String requestPayloadHash
) {}