package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.RegisterEdgeNodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.RecognitionUnitCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeRegistrationRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeUpdateRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeResponse;

/**
 * EdgeNodeController
 * <p>
 *     This controller handles the edge node operations for the parking system.
 *     It provides endpoints that are compatible with the parkup-edge-node Python service.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/edgenodes")
@RequiredArgsConstructor
@Tag(name = "Edge Nodes", description = "Edge Node Management Endpoints")
public class EdgeNodeController {

    private final RecognitionUnitCommandService recognitionUnitCommandService;

    @PostMapping
    @Operation(summary = "Register edge node", description = "Register a new edge node with network configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edge node registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Edge node already exists")
    })
    public ResponseEntity<EdgeNodeResponse> registerEdgeNode(@Valid @RequestBody EdgeNodeRegistrationRequest request) {
        try {
            String identifier = "edge_node_" + System.currentTimeMillis();
            
            Long defaultAffiliateId = 1L;
            Long defaultParkingLotId = 1L;
            
            RegisterEdgeNodeCommand command = RegisterEdgeNodeCommand.of(
                    request.url(),
                    request.port(),
                    identifier,
                    defaultAffiliateId,
                    defaultParkingLotId
            );
            
            RecognitionUnit recognitionUnit = recognitionUnitCommandService.handle(command);
            
            EdgeNodeResponse response = new EdgeNodeResponse(recognitionUnit.getId());
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update edge node", description = "Update an existing edge node with new network configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edge node updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Edge node not found")
    })
    public ResponseEntity<EdgeNodeResponse> updateEdgeNode(
            @PathVariable Long id,
            @Valid @RequestBody EdgeNodeUpdateRequest request) {
        try {
          
            EdgeNodeResponse response = new EdgeNodeResponse(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
