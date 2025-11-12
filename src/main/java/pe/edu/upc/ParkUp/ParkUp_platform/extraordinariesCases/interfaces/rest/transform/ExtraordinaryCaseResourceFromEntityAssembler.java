package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.aggregates.ExtraordinaryCase;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.resources.ExtraordinaryCaseResource;

public class ExtraordinaryCaseResourceFromEntityAssembler {
    public static ExtraordinaryCaseResource toResourceFromEntity(ExtraordinaryCase entity) {
        return new ExtraordinaryCaseResource(
                entity.getId(),
                entity.getParkingLotId(),
                entity.getRecognitionUnitId(),
                entity.getPlateNumber(),
                entity.getCaseReason(),
                entity.getStatus().name(), // "ENTERED", "OPENED", etc.
                entity.getCreatedAt().toString()
        );
    }
}