package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ControlBarrierCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.CreateRecognitionUnitCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.RegisterEdgeNodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.ProcessQrCodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.GenerateQrCodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.RecognitionUnitCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories.RecognitionUnitRepository;

import java.util.Optional;

/**
 * RecognitionUnitCommandServiceImpl
 * <p>
 *     This class implements the RecognitionUnitCommandService interface and provides
 *     the implementation for recognition unit command operations.
 * </p>
 */
@Service
@Transactional
public class RecognitionUnitCommandServiceImpl implements RecognitionUnitCommandService {

    private final RecognitionUnitRepository recognitionUnitRepository;

    public RecognitionUnitCommandServiceImpl(RecognitionUnitRepository recognitionUnitRepository) {
        this.recognitionUnitRepository = recognitionUnitRepository;
    }

    @Override
    public RecognitionUnit handle(CreateRecognitionUnitCommand command) {

        if (recognitionUnitRepository.existsByIdentifier(command.identifier())) {
            throw new RuntimeException("Recognition unit with identifier " + command.identifier() + " already exists");
        }

        RecognitionUnit recognitionUnit = new RecognitionUnit(
                command.identifier(),
                command.location(),
                command.affiliateId(),
                command.parkingLotId()
        );

        if (command.description() != null) {
            recognitionUnit.setDescription(command.description());
        }

        return recognitionUnitRepository.save(recognitionUnit);
    }

    @Override
    public Optional<RecognitionUnit> handle(ControlBarrierCommand command) {
        RecognitionUnit recognitionUnit = recognitionUnitRepository.findById(command.recognitionUnitId())
                .orElseThrow(() -> new RuntimeException("Recognition unit not found with ID: " + command.recognitionUnitId()));

        if (!recognitionUnit.isActive()) {
            throw new RuntimeException("Recognition unit is not active");
        }

        // Perform barrier action
        switch (command.action()) {
            case OPEN -> {
             
                System.out.println("Opening barrier for recognition unit: " + recognitionUnit.getIdentifier());
            }
            case CLOSE -> {
                
                System.out.println("Closing barrier for recognition unit: " + recognitionUnit.getIdentifier());
            }
        }

        return Optional.of(recognitionUnit);
    }

    @Override
    public RecognitionUnit handle(RegisterEdgeNodeCommand command) {

        if (recognitionUnitRepository.existsByIdentifier(command.identifier())) {
            throw new RuntimeException("Recognition unit with identifier " + command.identifier() + " already exists");
        }

    
        String serverCode = "EDGE_" + System.currentTimeMillis();

        RecognitionUnit recognitionUnit = new RecognitionUnit(
                command.identifier(),
                RecognitionUnit.Location.ENTRANCE, 
                command.affiliateId(),
                command.parkingLotId(),
                command.ipAddress(),
                command.port()
        );

        recognitionUnit.setServerCode(serverCode);

        return recognitionUnitRepository.save(recognitionUnit);
    }

    @Override
    public Optional<RecognitionUnit> handle(ProcessQrCodeCommand command) {

        String serverCode = command.getServerCode();
        String unitIdStr = command.getUnitId();

        if (serverCode == null || unitIdStr == null) {
            throw new RuntimeException("Invalid QR code format");
        }

        try {
            Long unitId = Long.parseLong(unitIdStr);
            
            RecognitionUnit recognitionUnit = recognitionUnitRepository.findById(unitId)
                    .orElseThrow(() -> new RuntimeException("Recognition unit not found with ID: " + unitId));

            if (!serverCode.equals(recognitionUnit.getServerCode())) {
                throw new RuntimeException("Invalid server code");
            }

            return Optional.of(recognitionUnit);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid unit ID in QR code");
        }
    }

    @Override
    public Optional<RecognitionUnit> handle(GenerateQrCodeCommand command) {
        RecognitionUnit recognitionUnit = recognitionUnitRepository.findById(command.recognitionUnitId())
                .orElseThrow(() -> new RuntimeException("Recognition unit not found with ID: " + command.recognitionUnitId()));

        if (recognitionUnit.getServerCode() == null) {
            String serverCode = "EDGE_" + System.currentTimeMillis();
            recognitionUnit.setServerCode(serverCode);
        }

        recognitionUnit.setQrCode(recognitionUnit.getFullCode());

        RecognitionUnit savedUnit = recognitionUnitRepository.save(recognitionUnit);
        return Optional.of(savedUnit);
    }
}
