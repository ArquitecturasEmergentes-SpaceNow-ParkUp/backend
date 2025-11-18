package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
// TypeReference not required for our current parsing; keep minimal imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates.ParkingLot;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingLotMap;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.AddParkingLotMapCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.CreateParkingLotCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.EditParkingLotMapCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.MapLayout;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingLotCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories.ParkingLotMapRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories.ParkingLotRepository;

import java.util.Objects;
import java.util.Optional;

/**
 * ParkingLotCommandServiceImpl
 * <p>
 *     Implementa las operaciones de comando para la gestión de estacionamientos.
 * </p>
 */

/**
 * ParkingLotCommandServiceImpl
 * <p>
 *     Implementa las operaciones de comando sobre el agregado ParkingLot.
 * </p>
 */

@Service
@Transactional
public class ParkingLotCommandServiceImpl implements ParkingLotCommandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingLotCommandServiceImpl.class);

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotMapRepository parkingLotMapRepository;
    private final pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceCommandService parkingSpaceCommandService;
    private final pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories.ParkingSpaceRepository parkingSpaceRepository;
    private final ObjectMapper objectMapper;

    public ParkingLotCommandServiceImpl(ParkingLotRepository parkingLotRepository,
                                        ParkingLotMapRepository parkingLotMapRepository,
                                        pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceCommandService parkingSpaceCommandService,
                                        pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories.ParkingSpaceRepository parkingSpaceRepository,
                                        ObjectMapper objectMapper) {
        this.parkingLotRepository = Objects.requireNonNull(parkingLotRepository);
        this.parkingLotMapRepository = Objects.requireNonNull(parkingLotMapRepository);
        this.parkingSpaceCommandService = Objects.requireNonNull(parkingSpaceCommandService);
        this.parkingSpaceRepository = Objects.requireNonNull(parkingSpaceRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }
    @Override
    public ParkingLot handle(CreateParkingLotCommand command) {
        Objects.requireNonNull(command, "CreateParkingLotCommand no puede ser null");

        // Crear agregado ParkingLot
        ParkingLot parkingLot = new ParkingLot(command.name(), command.address());

        // Persistir primero el ParkingLot para obtener id si es necesario
        ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);

        // Si viene un mapa inicial (MapLayout), crearlo y asociarlo
        if (command.initialMap() != null) {
            MapLayout layout = command.initialMap();
            ParkingLotMap map = new ParkingLotMap(layout);

            // Debugging: layout length and a preview so we can see if something unexpected is sent
            try {
                if (layout.getLayoutJson() != null) {
                    int len = layout.getLayoutJson().length();
                    String preview = layout.getLayoutJson().substring(0, Math.min(len, 200));
                    LOGGER.debug("Saving new map with layout length={} preview={}", len, preview.replaceAll("\r?\n", " "));
                }
            } catch (Exception ignored) {}
            ParkingLotMap savedMap = parkingLotMapRepository.save(map);

            // asociar en memoria y persistir el agregado (si la relación es mantenida y mapeada)
            savedParkingLot.addMap(savedMap);
            // re-save para reflejar la asociación si corresponde
            savedParkingLot = parkingLotRepository.save(savedParkingLot);

            // Try to import spaces using layout JSON if present
            try {
                importSpacesFromLayoutIfPresent(savedMap.getId(), layout.getLayoutJson());
            } catch (Exception e) {
                LOGGER.warn("Failed to auto-import spaces from layout for map {}: {}", savedMap.getId(), e.getMessage());
            }
        }

        return savedParkingLot;
    }

    @Override
    public ParkingLotMap handle(AddParkingLotMapCommand command) {
        Objects.requireNonNull(command, "AddParkingLotMapCommand no puede ser null");

        // Validar existencia del parking lot destino
        Long parkingLotId = Objects.requireNonNull(command.parkingLotId(), "parkingLotId no puede ser null");
        Optional<ParkingLot> maybeParkingLot = parkingLotRepository.findById(parkingLotId);
        if (maybeParkingLot.isEmpty()) {
            throw new IllegalArgumentException("No existe ParkingLot con id: " + parkingLotId);
        }
        ParkingLot parkingLot = maybeParkingLot.get();

        // Validar que el layout JSON es del formato esperado (filas / slots)
        validateLayoutStructure(command.layoutJson());

        // Construir MapLayout a partir del comando
        MapLayout layout = new MapLayout(command.totalSpaces(), command.disabilitySpaces(), command.layoutJson());
        try {
            if (layout.getLayoutJson() != null) {
                int len = layout.getLayoutJson().length();
                String preview = layout.getLayoutJson().substring(0, Math.min(len, 200));
                LOGGER.debug("Adding map to parking lot {} layout length={} preview={}", command.parkingLotId(), len, preview.replaceAll("\r?\n", " "));
            }
        } catch (Exception ignored) {}

        // Crear y persistir ParkingLotMap
        ParkingLotMap map = new ParkingLotMap(layout);
        ParkingLotMap savedMap = parkingLotMapRepository.save(map);

        // Asociar con el ParkingLot y persistir el agregado
        parkingLot.addMap(savedMap);
        parkingLotRepository.save(parkingLot);

        // Auto-import spaces for this newly created map
        try {
            importSpacesFromLayoutIfPresent(savedMap.getId(), layout.getLayoutJson());
        } catch (Exception e) {
            LOGGER.warn("Failed to auto-import spaces from layout for map {}: {}", savedMap.getId(), e.getMessage());
        }

        return savedMap;
    }

    @Override
    public ParkingLotMap handle(EditParkingLotMapCommand command) {
        Objects.requireNonNull(command, "EditParkingLotMapCommand no puede ser null");

        // Buscar el mapa por su MapId
        Long mapId = Objects.requireNonNull(command.mapId(), "mapId no puede ser null");
        Optional<ParkingLotMap> maybeMap = parkingLotMapRepository.findById(mapId);
        if (maybeMap.isEmpty()) {
            throw new IllegalArgumentException("ParkingLotMap con id " + mapId + " no encontrado");
        }
        ParkingLotMap existing = maybeMap.get();

        // Validar el layout antes de editar
        validateLayoutStructure(command.newLayoutJson());

        // Construir nuevo MapLayout con los nuevos valores
        MapLayout newLayout = new MapLayout(command.newTotalSpaces(), command.newDisabilitySpaces(), command.newLayoutJson());
        try {
            if (newLayout.getLayoutJson() != null) {
                int len = newLayout.getLayoutJson().length();
                String preview = newLayout.getLayoutJson().substring(0, Math.min(len, 200));
                LOGGER.debug("Editing map {} for parking lot {} layout length={} preview={}", command.mapId(), command.parkingLotId(), len, preview.replaceAll("\r?\n", " "));
            }
        } catch (Exception ignored) {}

        // Aplicar edición y persistir
        existing.editLayout(newLayout);
        ParkingLotMap saved = parkingLotMapRepository.save(existing);

        try {
            importSpacesFromLayoutIfPresent(saved.getId(), newLayout.getLayoutJson());
        } catch (Exception e) {
            LOGGER.warn("Failed to auto-import spaces from layout for map {}: {}", saved.getId(), e.getMessage());
        }

        return saved;
    }

    private void importSpacesFromLayoutIfPresent(Long mapId, String layoutJson) throws Exception {
        if (layoutJson == null || layoutJson.isBlank()) return;

        JsonNode root = objectMapper.readTree(layoutJson);
        if (!root.isArray()) return;

        var items = new java.util.ArrayList<pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.ImportParkingSpacesCommand.SpaceItem>();
        for (JsonNode row : root) {
            JsonNode slots = row.get("slots");
            if (slots != null && slots.isArray()) {
                for (JsonNode slot : slots) {
                    JsonNode ids = slot.get("ids");
                    if (ids != null && ids.isArray()) {
                        for (JsonNode idNode : ids) {
                            String code = idNode.asText();
                            if (code == null || code.isBlank()) continue;
                            // Avoid duplicating spaces (idempotent)
                            boolean exists = parkingSpaceRepository.findByMapIdAndCode(mapId, code).isPresent();
                            if (exists) continue;
                            boolean disability = java.util.regex.Pattern.compile("\\bDISABLED\\b", java.util.regex.Pattern.CASE_INSENSITIVE).matcher(code).find();
                            items.add(new pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.ImportParkingSpacesCommand.SpaceItem(code, disability));
                        }
                    }
                }
            }
        }

        if (!items.isEmpty()) {
            var command = new pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.ImportParkingSpacesCommand(mapId, items);
            parkingSpaceCommandService.handle(command);
        }
    }

    private void validateLayoutStructure(String layoutJson) {
        if (layoutJson == null || layoutJson.isBlank()) return;
        try {
            JsonNode root = objectMapper.readTree(layoutJson);
            if (!root.isArray()) {
                throw new IllegalArgumentException("Formato de layout inválido: se espera un arreglo de filas");
            }

            // Accept array of objects with { row: string, slots: [] }
            for (JsonNode node : root) {
                if (!node.isObject()) {
                    throw new IllegalArgumentException("Formato de layout inválido: cada fila debe ser un objeto");
                }
                if (!node.has("row") || !node.has("slots") || !node.get("slots").isArray()) {
                    throw new IllegalArgumentException("Formato de layout inválido: 'row' y 'slots' requeridos");
                }
            }
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Formato de layout inválido: JSON incorrecto", ex);
        }
    }

}