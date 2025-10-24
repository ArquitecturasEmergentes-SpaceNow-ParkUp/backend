package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.RegisterEdgeNodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.RecognitionUnitCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeRegistrationResource;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeResource;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.QrCodeResponse;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.RecognitionUnitResource;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.EdgeNodeCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.EdgeNodeRegistrationToEdgeNodeResourceMapper;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.RecognitionUnitResourceFromEntityAssembler;



@RestController
@RequestMapping("/api/v1/edge-nodes")
@RequiredArgsConstructor
public class EdgeNodeRegistrationController {

    private final RecognitionUnitCommandService recognitionUnitCommandService;
    private final EdgeNodeRegistrationToEdgeNodeResourceMapper registrationMapper;

    @PostMapping("/register")
    public ResponseEntity<RecognitionUnitResource> registerEdgeNode(
            @RequestBody EdgeNodeRegistrationResource registration) {

        EdgeNodeResource full = registrationMapper.toEdgeNodeResource(registration);
        RegisterEdgeNodeCommand command =
                EdgeNodeCommandFromResourceAssembler.toCommandFromResource(full);
        RecognitionUnit createdUnit = recognitionUnitCommandService.handle(command);

        RecognitionUnitResource resourceResponse =
                RecognitionUnitResourceFromEntityAssembler.toResourceFromEntity(createdUnit);

        // devolver JSON con id en la raíz
        return ResponseEntity.ok(resourceResponse);
    }
}
