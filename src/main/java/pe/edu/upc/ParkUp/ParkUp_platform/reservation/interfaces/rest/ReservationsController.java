package pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands.CancelReservationCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.commands.ConfirmPaymentCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries.GetActiveReservationByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries.GetReservationByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries.GetReservationsByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.services.ReservationCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.services.ReservationQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.rest.resources.CreateReservationRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.rest.resources.ReservationResource;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.rest.transform.CreateReservationCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.rest.transform.ReservationResourceFromEntityAssembler;

import java.util.List;

/**
 * Reservations controller
 * Handles reservation-related HTTP requests
 */
@RestController
@RequestMapping("/api/v1/reservations")
@Tag(name = "Reservations", description = "Reservation Management Endpoints")
public class ReservationsController {

    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;

    public ReservationsController(ReservationCommandService reservationCommandService,
                                  ReservationQueryService reservationQueryService) {
        this.reservationCommandService = reservationCommandService;
        this.reservationQueryService = reservationQueryService;
    }

    /**
     * Creates a new reservation
     *
     * @param request The create reservation request
     * @return The created reservation resource
     */
    @PostMapping
    @Operation(summary = "Create a new reservation", description = "Creates a new parking slot reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or parking slot not available"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<ReservationResource> createReservation(@Valid @RequestBody CreateReservationRequest request) {
        try {
            var command = CreateReservationCommandFromResourceAssembler.toCommandFromResource(request);
            var reservation = reservationCommandService.handle(command);

            if (reservation.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            var resource = ReservationResourceFromEntityAssembler.toResourceFromEntity(reservation.get());
            return new ResponseEntity<>(resource, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Gets a reservation by ID
     *
     * @param reservationId The reservation ID
     * @return The reservation resource
     */
    @GetMapping("/{reservationId}")
    @Operation(summary = "Get reservation by ID", description = "Retrieves a reservation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation found"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationResource> getReservationById(@PathVariable Long reservationId) {
        var query = new GetReservationByIdQuery(reservationId);
        var reservation = reservationQueryService.handle(query);

        if (reservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = ReservationResourceFromEntityAssembler.toResourceFromEntity(reservation.get());
        return ResponseEntity.ok(resource);
    }

    /**
     * Gets all reservations for a user
     *
     * @param userId The user ID
     * @return List of reservation resources
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get reservations by user", description = "Retrieves all reservations for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<ReservationResource>> getReservationsByUser(@PathVariable Long userId) {
        var query = new GetReservationsByUserIdQuery(userId);
        var reservations = reservationQueryService.handle(query);

        var resources = reservations.stream()
                .map(ReservationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    /**
     * Gets the active reservation for a user
     *
     * @param userId The user ID
     * @return The active reservation resource
     */
    @GetMapping("/user/{userId}/active")
    @Operation(summary = "Get active reservation", description = "Retrieves the active reservation for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active reservation found"),
            @ApiResponse(responseCode = "404", description = "No active reservation found")
    })
    public ResponseEntity<ReservationResource> getActiveReservationByUser(@PathVariable Long userId) {
        var query = new GetActiveReservationByUserIdQuery(userId);
        var reservation = reservationQueryService.handle(query);

        if (reservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = ReservationResourceFromEntityAssembler.toResourceFromEntity(reservation.get());
        return ResponseEntity.ok(resource);
    }

    /**
     * Confirms payment for a reservation (manual endpoint - usually triggered by payment event)
     *
     * @param reservationId The reservation ID
     * @return The updated reservation resource
     */
    @PutMapping("/{reservationId}/confirm-payment")
    @Operation(summary = "Confirm payment for reservation", description = "Updates reservation status from PENDING_PAYMENT to ACTIVE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment confirmed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid reservation status"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationResource> confirmPayment(@PathVariable Long reservationId) {
        try {
            var command = new ConfirmPaymentCommand(reservationId);
            var reservation = reservationCommandService.handle(command);

            if (reservation.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var resource = ReservationResourceFromEntityAssembler.toResourceFromEntity(reservation.get());
            return ResponseEntity.ok(resource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Cancels a reservation
     *
     * @param reservationId The reservation ID
     * @return The cancelled reservation resource
     */
    @DeleteMapping("/{reservationId}")
    @Operation(summary = "Cancel a reservation", description = "Cancels an existing reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Cannot cancel reservation - policy violation"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationResource> cancelReservation(@PathVariable Long reservationId) {
        try {
            var command = new CancelReservationCommand(reservationId);
            var reservation = reservationCommandService.handle(command);

            if (reservation.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var resource = ReservationResourceFromEntityAssembler.toResourceFromEntity(reservation.get());
            return ResponseEntity.ok(resource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
