package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.RecognitionUnitResource;

/**
 * RecognitionUnitResourceFromEntityAssembler
 * <p>
 *     This class is responsible for transforming RecognitionUnit entities to RecognitionUnitResource DTOs.
 * </p>
 */
public class RecognitionUnitResourceFromEntityAssembler {

    /**
     * Transform RecognitionUnit entity to RecognitionUnitResource
     * @param recognitionUnit the recognition unit entity
     * @return the recognition unit resource
     */
    public static RecognitionUnitResource toResourceFromEntity(RecognitionUnit recognitionUnit) {
        if (recognitionUnit == null) {
            return null;
        }

        return RecognitionUnitResource.of(
                recognitionUnit.getId(),
                recognitionUnit.getIdentifier(),
                recognitionUnit.getLocation() != null ? recognitionUnit.getLocation().name() : null,
                recognitionUnit.getStatus() != null ? recognitionUnit.getStatus().name() : null,
                recognitionUnit.getAffiliateId(),
                recognitionUnit.getParkingLotId(),
                recognitionUnit.getDescription(),
                recognitionUnit.getCreatedAt() != null ? recognitionUnit.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null,
                recognitionUnit.getUpdatedAt() != null ? recognitionUnit.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null
        );
    }
}
