package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.aggregates.Ticket;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetActiveTicketByLicensePlateQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetTicketByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.TicketCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.TicketQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.AutoEntryRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.AutoExitRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.TicketResource;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.ProcessAutoEntryCommandFromRequestAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.ProcessAutoExitCommandFromRequestAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.TicketResourceFromEntityAssembler;

import java.util.List;
import java.util.Optional;

/**
 * EntriesAndExitsController
 * <p>
 *     This controller handles the entries and exits operations for the parking system.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/entries-and-exits")
@Tag(name = "Entries and Exits", description = "Entries and Exits Management Endpoints")
public class EntriesAndExitsController {

    private final TicketCommandService ticketCommandService;
    private final TicketQueryService ticketQueryService;

    public EntriesAndExitsController(TicketCommandService ticketCommandService, 
                                   TicketQueryService ticketQueryService) {
        this.ticketCommandService = ticketCommandService;
        this.ticketQueryService = ticketQueryService;
    }

    @PostMapping("/auto-entry")
    @Operation(summary = "Process auto entry", description = "Process automatic entry of a vehicle into the parking lot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entry processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "409", description = "Vehicle already inside parking lot")
    })
    public ResponseEntity<TicketResource> processAutoEntry(@Valid @RequestBody AutoEntryRequest request) {
        try {
            var command = ProcessAutoEntryCommandFromRequestAssembler.toCommandFromRequest(request);
            Optional<Ticket> ticketOptional = ticketCommandService.handle(command);
            
            return ticketOptional
                    .map(TicketResourceFromEntityAssembler::toResourceFromEntity)
                    .map(resource -> ResponseEntity.status(HttpStatus.CREATED).body(resource))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/auto-exit")
    @Operation(summary = "Process auto exit", description = "Process automatic exit of a vehicle from the parking lot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exit processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "No active ticket found")
    })
    public ResponseEntity<TicketResource> processAutoExit(@Valid @RequestBody AutoExitRequest request) {
        try {
            var command = ProcessAutoExitCommandFromRequestAssembler.toCommandFromRequest(request);
            Optional<Ticket> ticketOptional = ticketCommandService.handle(command);
            
            return ticketOptional
                    .map(TicketResourceFromEntityAssembler::toResourceFromEntity)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tickets/{ticketId}")
    @Operation(summary = "Get ticket by ID", description = "Get a ticket by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket found"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public ResponseEntity<TicketResource> getTicketById(@PathVariable Long ticketId) {
        var query = GetTicketByIdQuery.of(ticketId);
        Optional<Ticket> ticketOptional = ticketQueryService.handle(query);
        
        return ticketOptional
                .map(TicketResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tickets/active/{licensePlate}")
    @Operation(summary = "Get active ticket by license plate", description = "Get active ticket for a specific license plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active ticket found"),
            @ApiResponse(responseCode = "404", description = "No active ticket found")
    })
    public ResponseEntity<TicketResource> getActiveTicketByLicensePlate(@PathVariable String licensePlate) {
        var query = GetActiveTicketByLicensePlateQuery.of(licensePlate);
        Optional<Ticket> ticketOptional = ticketQueryService.handle(query);
        
        return ticketOptional
                .map(TicketResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tickets/user/{userId}")
    @Operation(summary = "Get tickets by user ID", description = "Get all tickets for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets retrieved successfully")
    })
    public ResponseEntity<List<TicketResource>> getTicketsByUserId(@PathVariable Long userId) {
        List<Ticket> tickets = ticketQueryService.getAllTicketsByUserId(userId);
        List<TicketResource> ticketResources = tickets.stream()
                .map(TicketResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        
        return ResponseEntity.ok(ticketResources);
    }
}
