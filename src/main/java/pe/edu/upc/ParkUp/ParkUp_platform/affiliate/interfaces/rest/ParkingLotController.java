package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
// removed unused import
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates.ParkingLot;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.ParkingLotSummaryResource;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.ParkingLotDetailResource;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform.ParkingLotDetailResourceFromEntityAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingLotMap;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.AddParkingLotMapCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.CreateParkingLotCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.EditParkingLotMapCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.queries.GetAllParkingLotsQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.queries.GetParkingLotByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingLotCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingLotQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.AddParkingLotMapResource;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.CreateParkingLotResource;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.EditParkingLotMapResource;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.ImportParkingSpacesResource;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.UpdateParkingSpaceStatusResource;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform.AddParkingLotMapCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform.CreateParkingLotCommandFromResourceAssembler;
// removed unused import
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform.ImportParkingSpacesCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform.UpdateParkingSpaceStatusCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Affiliate Controller", description = "Operaciones relacionadas con afiliados y lotes de estacionamiento")
@RequestMapping("/api/affiliate/parking-lots")
public class ParkingLotController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingLotController.class);

    private final ParkingLotCommandService commandService;
    private final ParkingLotQueryService queryService;
    private final ParkingSpaceCommandService spaceCommandService;
    private final ParkingSpaceQueryService spaceQueryService;
    private final ParkingLotDetailResourceFromEntityAssembler parkingLotDetailResourceFromEntityAssembler;

    public ParkingLotController(ParkingLotCommandService commandService,
                                ParkingLotQueryService queryService,
                                ParkingSpaceCommandService spaceCommandService,
                                ParkingSpaceQueryService spaceQueryService,
                                ParkingLotDetailResourceFromEntityAssembler parkingLotDetailResourceFromEntityAssembler) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.spaceCommandService = spaceCommandService;
        this.spaceQueryService = spaceQueryService;
        this.parkingLotDetailResourceFromEntityAssembler = parkingLotDetailResourceFromEntityAssembler;
    }

    @PostMapping
    public ResponseEntity<ParkingLot> createParkingLot(@Valid @RequestBody CreateParkingLotResource resource) {
        try {
            CreateParkingLotCommand command = CreateParkingLotCommandFromResourceAssembler.toCommand(resource);
            ParkingLot created = commandService.handle(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear parking lot", ex);
        }
    }

    @GetMapping
    public ResponseEntity<List<ParkingLotSummaryResource>> getAllParkingLots() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth != null ? String.valueOf(auth.getName()) : "anonymous";
        List<ParkingLot> all = queryService.handle(new GetAllParkingLotsQuery());
        LOGGER.info("User '{}' requested /api/affiliate/parking-lots; returning {} lot(s)", currentUser, all.size());
        List<ParkingLotSummaryResource> summary = all.stream()
            .map(l -> new ParkingLotSummaryResource(l.getId(), l.getName(), l.getAddress()))
            .toList();
        return ResponseEntity.ok().header("X-Total-Parking-Lots", String.valueOf(summary.size())).body(summary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLotDetailResource> getParkingLotById(@PathVariable("id") Long id) {
        Optional<ParkingLot> maybe = queryService.handle(new GetParkingLotByIdQuery(id));
        if (maybe.isEmpty()) return ResponseEntity.notFound().build();
        var resource = parkingLotDetailResourceFromEntityAssembler.toResource(maybe.get());
        return ResponseEntity.ok(resource);
    }

    @PostMapping("/maps")
    public ResponseEntity<ParkingLotMap> addParkingLotMap(@Valid @RequestBody AddParkingLotMapResource resource) {
        try {
            AddParkingLotMapCommand command = AddParkingLotMapCommandFromResourceAssembler.toCommand(resource);
            ParkingLotMap saved = commandService.handle(command);
            if (saved != null && saved.getLayout() != null && saved.getLayout().getLayoutJson() != null) {
                LOGGER.debug("Map added for parking lot {} layout length={}", resource.getParkingLotId(), saved.getLayout().getLayoutJson().length());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al agregar mapa", ex);
        }
    }

    @PutMapping("/maps/{mapId}")
    public ResponseEntity<ParkingLotMap> editParkingLotMap(
            @PathVariable("mapId") Long mapId,
            @Valid @RequestBody EditParkingLotMapResource resource) {
        try {
            // asegurar coherencia entre path y body si body incluye mapId
            if (resource.getMapId() != null && !resource.getMapId().equals(mapId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mapId en path y body no coinciden");
            }
            // construir comando con el mapId del path para evitar inconsistencias
            EditParkingLotMapCommand command = new EditParkingLotMapCommand(
                    resource.getParkingLotId(),
                    mapId,
                    resource.getNewLayoutJson(),
                    resource.getNewTotalSpaces(),
                    resource.getNewDisabilitySpaces()
            );
            ParkingLotMap updated = commandService.handle(command);
                if (updated != null && updated.getLayout() != null && updated.getLayout().getLayoutJson() != null) {
                    LOGGER.debug("Map edited for map {} layout length={}", mapId, updated.getLayout().getLayoutJson().length());
                }
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al editar mapa", ex);
        }
    }

    @PostMapping("/maps/{mapId}/spaces/import")
    public ResponseEntity<List<ParkingSpace>> importParkingSpaces(
            @PathVariable("mapId") Long mapId,
            @Valid @RequestBody ImportParkingSpacesResource resource) {
        try {
            if (resource.getMapId() != null && !resource.getMapId().equals(mapId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mapId en path y body no coinciden");
            }
            resource.setMapId(mapId);
            var command = ImportParkingSpacesCommandFromResourceAssembler.toCommand(resource);
            var saved = spaceCommandService.handle(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al importar espacios", ex);
        }
    }

    @GetMapping("/maps/{mapId}/spaces")
    public ResponseEntity<List<ParkingSpace>> listParkingSpaces(@PathVariable("mapId") Long mapId) {
        var spaces = spaceQueryService.getByMapId(mapId);
        return ResponseEntity.ok(spaces);
    }

    @PutMapping("/maps/{mapId}/spaces/{spaceId}/status")
    public ResponseEntity<ParkingSpace> updateParkingSpaceStatus(
            @PathVariable("mapId") Long mapId,
            @PathVariable("spaceId") Long spaceId,
            @Valid @RequestBody UpdateParkingSpaceStatusResource resource) {
        try {
            var command = UpdateParkingSpaceStatusCommandFromResourceAssembler.toCommand(spaceId, resource);
            var updated = spaceCommandService.handle(command);
            if (!updated.getMap().getId().equals(mapId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "spaceId no pertenece al mapId indicado");
            }
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar estado", ex);
        }
    }
}
