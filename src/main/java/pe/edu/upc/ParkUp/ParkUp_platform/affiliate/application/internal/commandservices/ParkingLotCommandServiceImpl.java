package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates.ParkingLot;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingLotMap;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.AddParkingLotMapCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.CreateParkingLotCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.EditParkingLotMapCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.MapId;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.MapLayout;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.ParkingLotId;
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

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotMapRepository parkingLotMapRepository;

    public ParkingLotCommandServiceImpl(ParkingLotRepository parkingLotRepository,
                                        ParkingLotMapRepository parkingLotMapRepository) {
        this.parkingLotRepository = Objects.requireNonNull(parkingLotRepository);
        this.parkingLotMapRepository = Objects.requireNonNull(parkingLotMapRepository);
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
            ParkingLotMap savedMap = parkingLotMapRepository.save(map);

            // asociar en memoria y persistir el agregado (si la relación es mantenida y mapeada)
            savedParkingLot.addMap(savedMap);
            // re-save para reflejar la asociación si corresponde
            savedParkingLot = parkingLotRepository.save(savedParkingLot);
        }

        return savedParkingLot;
    }

    @Override
    public ParkingLotMap handle(AddParkingLotMapCommand command) {
        Objects.requireNonNull(command, "AddParkingLotMapCommand no puede ser null");

        // Validar existencia del parking lot destino
        ParkingLotId plIdVo = new ParkingLotId(Objects.requireNonNull(command.parkingLotId(), "parkingLotId no puede ser null"));
        Optional<ParkingLot> maybeParkingLot = parkingLotRepository.findById(plIdVo);
        if (maybeParkingLot.isEmpty()) {
            throw new IllegalArgumentException("No existe ParkingLot con id: " + plIdVo.getValue());
        }
        ParkingLot parkingLot = maybeParkingLot.get();

        // Construir MapLayout a partir del comando
        MapLayout layout = new MapLayout(command.totalSpaces(), command.disabilitySpaces(), command.layoutJson());

        // Crear y persistir ParkingLotMap
        ParkingLotMap map = new ParkingLotMap(layout);
        ParkingLotMap savedMap = parkingLotMapRepository.save(map);

        // Asociar con el ParkingLot y persistir el agregado
        parkingLot.addMap(savedMap);
        parkingLotRepository.save(parkingLot);

        return savedMap;
    }

    @Override
    public ParkingLotMap handle(EditParkingLotMapCommand command) {
        Objects.requireNonNull(command, "EditParkingLotMapCommand no puede ser null");

        // Buscar el mapa por su MapId
        MapId mapIdVo = new MapId(Objects.requireNonNull(command.mapId(), "mapId no puede ser null"));
        Optional<ParkingLotMap> maybeMap = parkingLotMapRepository.findById(mapIdVo);
        if (maybeMap.isEmpty()) {
            throw new IllegalArgumentException("ParkingLotMap con id " + mapIdVo.getValue() + " no encontrado");
        }
        ParkingLotMap existing = maybeMap.get();

        // Construir nuevo MapLayout con los nuevos valores
        MapLayout newLayout = new MapLayout(command.newTotalSpaces(), command.newDisabilitySpaces(), command.newLayoutJson());

        // Aplicar edición y persistir
        existing.editLayout(newLayout);
        ParkingLotMap saved = parkingLotMapRepository.save(existing);

        return saved;
    }
}