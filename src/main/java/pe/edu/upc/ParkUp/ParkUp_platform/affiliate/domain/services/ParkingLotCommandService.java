package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates.ParkingLot;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingLotMap;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.AddParkingLotMapCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.CreateParkingLotCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands.EditParkingLotMapCommand;

public interface ParkingLotCommandService {
    ParkingLot handle(CreateParkingLotCommand command);
    ParkingLotMap handle(AddParkingLotMapCommand command);
    ParkingLotMap handle(EditParkingLotMapCommand command);
}
