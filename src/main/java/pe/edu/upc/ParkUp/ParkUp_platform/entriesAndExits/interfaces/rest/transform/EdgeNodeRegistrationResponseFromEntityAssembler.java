package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeRegistrationResponse;

/**
 * EdgeNodeRegistrationResponseFromEntityAssembler
 * <p>
 *     This class is responsible for transforming RecognitionUnit entities to EdgeNodeRegistrationResponse DTOs.
 * </p>
 */
public class EdgeNodeRegistrationResponseFromEntityAssembler {

    /**
     * Transform RecognitionUnit entity to EdgeNodeRegistrationResponse
     * @param recognitionUnit the recognition unit entity
     * @return the edge node registration response
     */
    public static EdgeNodeRegistrationResponse toResourceFromEntity(RecognitionUnit recognitionUnit) {
        if (recognitionUnit == null) {
            return null;
        }

        return EdgeNodeRegistrationResponse.of(
                recognitionUnit.getServerCode(),
                recognitionUnit.getId(),
                "Edge Node registered successfully"
        );
    }
}
