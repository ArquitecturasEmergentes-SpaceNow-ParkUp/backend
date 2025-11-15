package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.ImportParkingSpacesCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.UpdateParkingSpaceStatusCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;

import java.util.List;

public interface ParkingSpaceCommandService {
    List<ParkingSpace> handle(ImportParkingSpacesCommand command);
    ParkingSpace handle(UpdateParkingSpaceStatusCommand command);
}

