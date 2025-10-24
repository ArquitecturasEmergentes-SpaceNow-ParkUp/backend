package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.RegisterEdgeNodeCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.RecognitionUnitCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeRegistrationResource;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeResource;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform.EdgeNodeRegistrationToEdgeNodeResourceMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EdgeNodeRegistrationControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private RecognitionUnitCommandService recognitionUnitCommandService;

    @Mock
    private EdgeNodeRegistrationToEdgeNodeResourceMapper registrationMapper;

    @InjectMocks
    private EdgeNodeRegistrationController controller;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void registerEdgeNode_shouldReturnRecognitionUnitResource() throws Exception {
        // Arrange

        // JSON that Edge Node sends (minimal: ip + port)
        String requestJson = "{\"ip\":\"192.168.1.10\",\"port\":8080}";

        // The mapper should produce a full EdgeNodeResource (we mock it)
        EdgeNodeResource edgeNodeResource = new EdgeNodeResource(
                "192.168.1.10",    // ip
                8080,              // port
                "UNIT-ABC",        // identifier
                42L,               // affiliateId
                7L                 // parkingLotId
        );

        // RecognitionUnit that the command service will return
        RecognitionUnit created = new RecognitionUnit();
        created.setId(123L);
        created.setIdentifier("UNIT-ABC");
        created.setServerCode("EDGECODE1");
        created.setQrCode(created.getServerCode() + "-" + created.getId());
        created.setIpAddress("192.168.1.10");
        created.setPort(8080);
        created.setAffiliateId(42L);
        created.setParkingLotId(7L);
        created.setStatus(RecognitionUnit.Status.ACTIVE);

        // Mock behaviors
        when(registrationMapper.toEdgeNodeResource(any(EdgeNodeRegistrationResource.class)))
                .thenReturn(edgeNodeResource);

        when(recognitionUnitCommandService.handle(any(RegisterEdgeNodeCommand.class)))
                .thenReturn(created);

        // Act & Assert
        mockMvc.perform(post("/api/v1/edge-nodes/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                // verify basic fields in JSON response (adjust names if your assembler uses different names)
                .andExpect(jsonPath("$.id").value(created.getId().intValue()))
                .andExpect(jsonPath("$.identifier").value(created.getIdentifier()))
                .andExpect(jsonPath("$.location").value(created.getLocation()))
                .andExpect(jsonPath("$.status").value(created.getStatus().toString()))
                .andExpect(jsonPath("$.affiliateId").value(created.getAffiliateId().intValue()))
                .andExpect(jsonPath("$.parkingLotId").value(created.getParkingLotId().intValue()))
                .andExpect(jsonPath("$.description").value(created.getDescription()));


    }
}