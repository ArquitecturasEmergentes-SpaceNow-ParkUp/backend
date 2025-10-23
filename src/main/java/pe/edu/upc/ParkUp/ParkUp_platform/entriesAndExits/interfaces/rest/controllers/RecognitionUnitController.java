package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.GenerateQrCodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetAllRecognitionUnitsByAffiliateQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetRecognitionUnitByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.RecognitionUnitCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.RecognitionUnitQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.EdgeNodeCommunicationService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.MakeEdgeNodeRequestCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.BarrierControlRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.CreateRecognitionUnitRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.RecognitionUnitResource;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.RegisterEdgeNodeRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.ProcessQrCodeRequest;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.QrCodeResponse;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeRegistrationResponse;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.ControlBarrierCommandFromRequestAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.CreateRecognitionUnitCommandFromRequestAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.RecognitionUnitResourceFromEntityAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.RegisterEdgeNodeCommandFromRequestAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.ProcessQrCodeCommandFromRequestAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.EdgeNodeRegistrationResponseFromEntityAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.QrCodeResponseFromEntityAssembler;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpMethod;

/**
 * RecognitionUnitController
 * <p>
 *     This controller handles the recognition unit operations for the parking system.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/recognition-units")
@Tag(name = "Recognition Units", description = "Recognition Units Management Endpoints")
public class RecognitionUnitController {

    private final RecognitionUnitCommandService recognitionUnitCommandService;
    private final RecognitionUnitQueryService recognitionUnitQueryService;
    private final EdgeNodeCommunicationService edgeNodeCommunicationService;

    public RecognitionUnitController(RecognitionUnitCommandService recognitionUnitCommandService,
                                   RecognitionUnitQueryService recognitionUnitQueryService,
                                   EdgeNodeCommunicationService edgeNodeCommunicationService) {
        this.recognitionUnitCommandService = recognitionUnitCommandService;
        this.recognitionUnitQueryService = recognitionUnitQueryService;
        this.edgeNodeCommunicationService = edgeNodeCommunicationService;
    }

    @PostMapping
    @Operation(summary = "Create recognition unit", description = "Create a new recognition unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recognition unit created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Recognition unit already exists")
    })
    public ResponseEntity<RecognitionUnitResource> createRecognitionUnit(@Valid @RequestBody CreateRecognitionUnitRequest request) {
        try {
            var command = CreateRecognitionUnitCommandFromRequestAssembler.toCommandFromRequest(request);
            RecognitionUnit recognitionUnit = recognitionUnitCommandService.handle(command);
            RecognitionUnitResource resource = RecognitionUnitResourceFromEntityAssembler.toResourceFromEntity(recognitionUnit);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{recognitionUnitId}")
    @Operation(summary = "Get recognition unit by ID", description = "Get a recognition unit by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recognition unit found"),
            @ApiResponse(responseCode = "404", description = "Recognition unit not found")
    })
    public ResponseEntity<RecognitionUnitResource> getRecognitionUnitById(@PathVariable Long recognitionUnitId) {
        var query = GetRecognitionUnitByIdQuery.of(recognitionUnitId);
        Optional<RecognitionUnit> recognitionUnitOptional = recognitionUnitQueryService.handle(query);
        
        return recognitionUnitOptional
                .map(RecognitionUnitResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all recognition units", description = "Get all recognition units")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recognition units retrieved successfully")
    })
    public ResponseEntity<List<RecognitionUnitResource>> getAllRecognitionUnits() {
        List<RecognitionUnit> recognitionUnits = recognitionUnitQueryService.getAllRecognitionUnits();
        List<RecognitionUnitResource> resources = recognitionUnits.stream()
                .map(RecognitionUnitResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/affiliate/{affiliateId}")
    @Operation(summary = "Get recognition units by affiliate", description = "Get all recognition units for a specific affiliate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recognition units retrieved successfully")
    })
    public ResponseEntity<List<RecognitionUnitResource>> getRecognitionUnitsByAffiliate(@PathVariable Long affiliateId) {
        var query = GetAllRecognitionUnitsByAffiliateQuery.of(affiliateId);
        List<RecognitionUnit> recognitionUnits = recognitionUnitQueryService.handle(query);
        List<RecognitionUnitResource> resources = recognitionUnits.stream()
                .map(RecognitionUnitResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        
        return ResponseEntity.ok(resources);
    }

    @PostMapping("/{recognitionUnitId}/barrier-control")
    @Operation(summary = "Control barrier", description = "Control barrier (open/close) for a recognition unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barrier controlled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Recognition unit not found")
    })
    public ResponseEntity<RecognitionUnitResource> controlBarrier(@PathVariable Long recognitionUnitId,
                                                                @Valid @RequestBody BarrierControlRequest request) {
        try {
            // Ensure the recognition unit ID in the path matches the one in the request
            if (!recognitionUnitId.equals(request.recognitionUnitId())) {
                return ResponseEntity.badRequest().build();
            }
            
            var command = ControlBarrierCommandFromRequestAssembler.toCommandFromRequest(request);
            Optional<RecognitionUnit> recognitionUnitOptional = recognitionUnitCommandService.handle(command);
            
            return recognitionUnitOptional
                    .map(RecognitionUnitResourceFromEntityAssembler::toResourceFromEntity)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Register edge node", description = "Register a new edge node with network configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Edge node registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Edge node already exists")
    })
    public ResponseEntity<EdgeNodeRegistrationResponse> registerEdgeNode(@Valid @RequestBody RegisterEdgeNodeRequest request) {
        try {
            var command = RegisterEdgeNodeCommandFromRequestAssembler.toCommandFromRequest(request);
            RecognitionUnit recognitionUnit = recognitionUnitCommandService.handle(command);
            EdgeNodeRegistrationResponse response = EdgeNodeRegistrationResponseFromEntityAssembler.toResourceFromEntity(recognitionUnit);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/process-qr")
    @Operation(summary = "Process QR code", description = "Process a scanned QR code and identify the recognition unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QR code processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid QR code"),
            @ApiResponse(responseCode = "404", description = "Recognition unit not found")
    })
    public ResponseEntity<RecognitionUnitResource> processQrCode(@Valid @RequestBody ProcessQrCodeRequest request) {
        try {
            var command = ProcessQrCodeCommandFromRequestAssembler.toCommandFromRequest(request);
            Optional<RecognitionUnit> recognitionUnitOptional = recognitionUnitCommandService.handle(command);
            
            return recognitionUnitOptional
                    .map(RecognitionUnitResourceFromEntityAssembler::toResourceFromEntity)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{recognitionUnitId}/generate-qr")
    @Operation(summary = "Generate QR code", description = "Generate QR code for a specific recognition unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QR code generated successfully"),
            @ApiResponse(responseCode = "404", description = "Recognition unit not found")
    })
    public ResponseEntity<QrCodeResponse> generateQrCode(@PathVariable Long recognitionUnitId) {
        try {
            var command = GenerateQrCodeCommand.of(recognitionUnitId);
            Optional<RecognitionUnit> recognitionUnitOptional = recognitionUnitCommandService.handle(command);
            
            return recognitionUnitOptional
                    .map(QrCodeResponseFromEntityAssembler::toResourceFromEntity)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{recognitionUnitId}/request")
    @Operation(summary = "Make request to Edge Node", description = "Send HTTP request to a specific Edge Node")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or Edge Node not found"),
            @ApiResponse(responseCode = "500", description = "Failed to communicate with Edge Node")
    })
    public ResponseEntity<String> makeEdgeNodeRequest(
            @PathVariable Long recognitionUnitId,
            @RequestParam String endpoint,
            @RequestParam(defaultValue = "GET") HttpMethod method,
            @RequestBody(required = false) String requestBody) {
        
        try {
            var command = MakeEdgeNodeRequestCommand.of(recognitionUnitId, endpoint, method, requestBody);
            String response = edgeNodeCommunicationService.makeRequest(command);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error making request: " + e.getMessage());
        }
    }
}
