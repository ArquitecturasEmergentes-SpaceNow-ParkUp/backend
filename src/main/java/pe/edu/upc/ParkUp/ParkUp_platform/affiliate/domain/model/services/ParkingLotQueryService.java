package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.services;

import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates.ParkingLot;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.queries.GetAllParkingLotsQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.queries.GetParkingLotByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ParkingLotQueryService {
    Optional<ParkingLot> handle(GetParkingLotByIdQuery query);
    List<ParkingLot> handle(GetAllParkingLotsQuery query);
}