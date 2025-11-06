package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.ValidationException;

import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates.MapLayout;

public interface ParkingLotValidatorService {
    void validateMapLayout(MapLayout layout) throws ValidationException;
}