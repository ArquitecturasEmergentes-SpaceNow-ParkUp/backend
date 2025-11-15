package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.UpdateParkingSpaceStatusCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceCommandService;

@Service
public class AffiliateContextFacade {
    private final ParkingSpaceCommandService parkingSpaceCommandService;

    public AffiliateContextFacade(ParkingSpaceCommandService parkingSpaceCommandService) {
        this.parkingSpaceCommandService = parkingSpaceCommandService;
    }

    public void reserveSpace(Long spaceId) {
        parkingSpaceCommandService.handle(new UpdateParkingSpaceStatusCommand(spaceId, ParkingSpace.SpaceStatus.RESERVED));
    }

    public void occupySpace(Long spaceId) {
        parkingSpaceCommandService.handle(new UpdateParkingSpaceStatusCommand(spaceId, ParkingSpace.SpaceStatus.OCCUPIED));
    }

    public void releaseSpace(Long spaceId) {
        parkingSpaceCommandService.handle(new UpdateParkingSpaceStatusCommand(spaceId, ParkingSpace.SpaceStatus.AVAILABLE));
    }
}

