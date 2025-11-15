package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.ImportParkingSpacesCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.UpdateParkingSpaceStatusCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingLotMap;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories.ParkingLotMapRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.infrastructure.persistence.jpa.repositories.ParkingSpaceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ParkingSpaceCommandServiceImpl implements ParkingSpaceCommandService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingLotMapRepository parkingLotMapRepository;

    public ParkingSpaceCommandServiceImpl(ParkingSpaceRepository parkingSpaceRepository,
                                          ParkingLotMapRepository parkingLotMapRepository) {
        this.parkingSpaceRepository = Objects.requireNonNull(parkingSpaceRepository);
        this.parkingLotMapRepository = Objects.requireNonNull(parkingLotMapRepository);
    }

    @Override
    public List<ParkingSpace> handle(ImportParkingSpacesCommand command) {
        var map = parkingLotMapRepository.findById(command.mapId())
                .orElseThrow(() -> new IllegalArgumentException("ParkingLotMap no encontrado"));

        List<ParkingSpace> saved = new ArrayList<>();
        for (var item : command.items()) {
            var space = new ParkingSpace(item.code(), item.disability(), map);
            saved.add(parkingSpaceRepository.save(space));
            map.getLayout().addSpaceCode(item.code());
        }
        parkingLotMapRepository.save(map);
        return saved;
    }

    @Override
    public ParkingSpace handle(UpdateParkingSpaceStatusCommand command) {
        var space = parkingSpaceRepository.findById(command.spaceId())
                .orElseThrow(() -> new IllegalArgumentException("ParkingSpace no encontrado"));
        space.setStatus(command.status());
        return parkingSpaceRepository.save(space);
    }
}
