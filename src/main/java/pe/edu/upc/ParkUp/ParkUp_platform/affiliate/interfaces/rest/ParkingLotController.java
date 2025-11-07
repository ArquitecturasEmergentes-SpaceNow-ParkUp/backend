package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.application.internal.queryservices.ParkingLotQueryServiceImpl;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates.ParkingLot;
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
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform.AddParkingLotMapCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform.CreateParkingLotCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform.EditParkingLotMapCommandFromResourceAssembler;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Affiliate Controller", description = "Operaciones relacionadas con afiliados y lotes de estacionamiento")
@RequestMapping("/api/affiliate/parking-lots")
public class ParkingLotController {

    private final ParkingLotCommandService commandService;
    private final ParkingLotQueryService queryService;

    public ParkingLotController(ParkingLotCommandService commandService,
                                ParkingLotQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
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
    public ResponseEntity<List<ParkingLot>> getAllParkingLots() {
        List<ParkingLot> all = queryService.handle(new GetAllParkingLotsQuery());
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLot> getParkingLotById(@PathVariable("id") Long id) {
        Optional<ParkingLot> maybe = queryService.handle(new GetParkingLotByIdQuery(id));
        return maybe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/maps")
    public ResponseEntity<ParkingLotMap> addParkingLotMap(@Valid @RequestBody AddParkingLotMapResource resource) {
        try {
            AddParkingLotMapCommand command = AddParkingLotMapCommandFromResourceAssembler.toCommand(resource);
            ParkingLotMap saved = commandService.handle(command);
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
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al editar mapa", ex);
        }
    }
}
