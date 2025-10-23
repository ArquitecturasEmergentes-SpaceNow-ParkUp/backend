package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ProcessQrCodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.ProcessQrCodeRequest;

/**
 * ProcessQrCodeCommandFromRequestAssembler
 * <p>
 *     This class is responsible for transforming ProcessQrCodeRequest DTOs to ProcessQrCodeCommand domain objects.
 * </p>
 */
public class ProcessQrCodeCommandFromRequestAssembler {

    /**
     * Transform ProcessQrCodeRequest to ProcessQrCodeCommand
     * @param request the process QR code request
     * @return the process QR code command
     */
    public static ProcessQrCodeCommand toCommandFromRequest(ProcessQrCodeRequest request) {
        if (request == null) {
            return null;
        }

        return ProcessQrCodeCommand.of(request.fullCode());
    }
}
