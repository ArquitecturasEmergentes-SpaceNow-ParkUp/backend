package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.QrCodeResponse;

/**
 * QrCodeResponseFromEntityAssembler
 * <p>
 *     This class is responsible for transforming RecognitionUnit entities to QrCodeResponse DTOs.
 * </p>
 */
public class QrCodeResponseFromEntityAssembler {

    /**
     * Transform RecognitionUnit entity to QrCodeResponse
     * @param recognitionUnit the recognition unit entity
     * @return the QR code response
     */
    public static QrCodeResponse toResourceFromEntity(RecognitionUnit recognitionUnit) {
        if (recognitionUnit == null) {
            return null;
        }

        return QrCodeResponse.of(
                recognitionUnit.getFullCode(),
                recognitionUnit.getQrCode(),
                recognitionUnit.getId(),
                recognitionUnit.getServerCode()
        );
    }
}
