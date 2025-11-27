package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.UpdateParkingSpaceStatusCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceCommandService;

@Service
public class AffiliateContextFacade {
    private final ParkingSpaceCommandService parkingSpaceCommandService;
    private final pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceQueryService parkingSpaceQueryService;

    public AffiliateContextFacade(ParkingSpaceCommandService parkingSpaceCommandService,
            pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceQueryService parkingSpaceQueryService) {
        this.parkingSpaceCommandService = parkingSpaceCommandService;
        this.parkingSpaceQueryService = parkingSpaceQueryService;
    }

    public void reserveSpace(Long spaceId) {
        parkingSpaceCommandService
                .handle(new UpdateParkingSpaceStatusCommand(spaceId, ParkingSpace.SpaceStatus.RESERVED));
    }

    public void occupySpace(Long spaceId) {
        parkingSpaceCommandService
                .handle(new UpdateParkingSpaceStatusCommand(spaceId, ParkingSpace.SpaceStatus.OCCUPIED));
    }

    public void releaseSpace(Long spaceId) {
        parkingSpaceCommandService
                .handle(new UpdateParkingSpaceStatusCommand(spaceId, ParkingSpace.SpaceStatus.AVAILABLE));
    }

    public boolean isSpaceDisabledOnly(Long spaceId) {
        var space = parkingSpaceQueryService.getById(spaceId);
        return space.map(ParkingSpace::isDisability).orElse(false);
    }
}
