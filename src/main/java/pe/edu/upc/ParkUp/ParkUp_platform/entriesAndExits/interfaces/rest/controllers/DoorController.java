package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.controllers;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.MakeEdgeNodeRequestCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.EdgeNodeCommunicationService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories.RecognitionUnitRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.OpenDoorResource;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.OpenDoorCommandFromResourceAssembler;

@RestController
@RequestMapping("/api/v1/edge-nodes")
public class DoorController {

    private final EdgeNodeCommunicationService edgeNodeService;
    private final RecognitionUnitRepository recognitionUnitRepository;

    public DoorController(EdgeNodeCommunicationService edgeNodeService,
                          RecognitionUnitRepository recognitionUnitRepository) {
        this.edgeNodeService = edgeNodeService;
        this.recognitionUnitRepository = recognitionUnitRepository;
    }

    @PostMapping("/open-door")
    public ResponseEntity<String> openDoor(@RequestBody OpenDoorResource resource) {
        String[] parts = resource.fullCode().replace("\"", "").split("-");
        if (parts.length != 2) {
            return ResponseEntity.badRequest().body("Formato inválido de full_code");
        }

        String code = parts[0];
        Long unitId = Long.parseLong(parts[1]);

        RecognitionUnit unit = recognitionUnitRepository.findById(unitId)
                .orElseThrow(() -> new IllegalArgumentException("Unidad no encontrada"));

        if (!code.equals(unit.getQrCode())) {
            return ResponseEntity.status(403).body("Código inválido");
        }

        MakeEdgeNodeRequestCommand command = OpenDoorCommandFromResourceAssembler.toCommand(unit);
        String response = edgeNodeService.makeRequest(command);
        return ResponseEntity.ok(response);
    }
}