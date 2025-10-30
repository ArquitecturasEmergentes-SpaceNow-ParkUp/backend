package pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands.DeactivateDeviceCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.commands.MarkNotificationAsReadCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.queries.GetNotificationsByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.queries.GetUnreadNotificationsByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.model.queries.GetUserDevicesByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.services.NotificationCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.domain.services.NotificationQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.resources.NotificationResource;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.resources.RegisterDeviceRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.resources.UserDeviceResource;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.transform.RegisterDeviceCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.notification.interfaces.rest.transform.UserDeviceResourceFromEntityAssembler;

import java.util.List;

/**
 * Notifications controller
 * Handles notification-related HTTP requests
 */
@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications", description = "Notification Management Endpoints")
public class NotificationsController {

    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;

    public NotificationsController(NotificationCommandService notificationCommandService,
                                   NotificationQueryService notificationQueryService) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
    }

    /**
     * Gets all notifications for a user
     *
     * @param userId The user ID
     * @return List of notifications
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user notifications", description = "Retrieves all notifications for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<NotificationResource>> getNotificationsByUser(@PathVariable Long userId) {
        var query = new GetNotificationsByUserIdQuery(userId);
        var notifications = notificationQueryService.handle(query);

        var resources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    /**
     * Gets unread notifications for a user
     *
     * @param userId The user ID
     * @return List of unread notifications
     */
    @GetMapping("/user/{userId}/unread")
    @Operation(summary = "Get unread notifications", description = "Retrieves unread notifications for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unread notifications found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<NotificationResource>> getUnreadNotificationsByUser(@PathVariable Long userId) {
        var query = new GetUnreadNotificationsByUserIdQuery(userId);
        var notifications = notificationQueryService.handle(query);

        var resources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    /**
     * Marks a notification as read
     *
     * @param notificationId The notification ID
     * @param userId         The user ID (from auth context)
     * @return The updated notification
     */
    @PutMapping("/{notificationId}/read")
    @Operation(summary = "Mark notification as read", description = "Marks a notification as read by the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marked as read"),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "403", description = "User does not own this notification")
    })
    public ResponseEntity<NotificationResource> markNotificationAsRead(
            @PathVariable Long notificationId,
            @RequestParam Long userId) {
        try {
            var command = new MarkNotificationAsReadCommand(notificationId, userId);
            var notification = notificationCommandService.handle(command);

            if (notification.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var resource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
            return ResponseEntity.ok(resource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Registers a device for push notifications
     *
     * @param request The register device request
     * @return The registered device
     */
    @PostMapping("/devices")
    @Operation(summary = "Register device", description = "Registers a user device for push notifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<UserDeviceResource> registerDevice(@Valid @RequestBody RegisterDeviceRequest request) {
        try {
            var command = RegisterDeviceCommandFromResourceAssembler.toCommandFromResource(request);
            var device = notificationCommandService.handle(command);

            if (device.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            var resource = UserDeviceResourceFromEntityAssembler.toResourceFromEntity(device.get());
            return new ResponseEntity<>(resource, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Gets all devices for a user
     *
     * @param userId The user ID
     * @return List of devices
     */
    @GetMapping("/devices/user/{userId}")
    @Operation(summary = "Get user devices", description = "Retrieves all registered devices for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devices found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<UserDeviceResource>> getUserDevices(@PathVariable Long userId) {
        var query = new GetUserDevicesByUserIdQuery(userId);
        var devices = notificationQueryService.handle(query);

        var resources = devices.stream()
                .map(UserDeviceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    /**
     * Deactivates a device
     *
     * @param deviceId The device ID
     * @param userId   The user ID (from auth context)
     * @return The deactivated device
     */
    @DeleteMapping("/devices/{deviceId}")
    @Operation(summary = "Deactivate device", description = "Deactivates a registered device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "403", description = "User does not own this device")
    })
    public ResponseEntity<UserDeviceResource> deactivateDevice(
            @PathVariable Long deviceId,
            @RequestParam Long userId) {
        try {
            var command = new DeactivateDeviceCommand(deviceId, userId);
            var device = notificationCommandService.handle(command);

            if (device.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var resource = UserDeviceResourceFromEntityAssembler.toResourceFromEntity(device.get());
            return ResponseEntity.ok(resource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
