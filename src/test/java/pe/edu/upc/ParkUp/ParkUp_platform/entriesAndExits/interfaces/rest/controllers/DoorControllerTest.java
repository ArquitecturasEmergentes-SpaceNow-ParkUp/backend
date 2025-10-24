package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.EdgeNodeCommunicationService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories.RecognitionUnitRepository;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.OpenDoorResource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DoorControllerTest {

    @Mock
    private EdgeNodeCommunicationService edgeNodeService;

    @Mock
    private RecognitionUnitRepository recognitionUnitRepository;

    @InjectMocks
    private DoorController doorController;

    private RecognitionUnit recognitionUnit;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        recognitionUnit = new RecognitionUnit();
        recognitionUnit.setId(1L);
        recognitionUnit.setQrCode("ABC123");

        mockMvc = MockMvcBuilders.standaloneSetup(doorController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void openDoor_ShouldReturnOkWithFullResponse() throws Exception {
        // Arrange
        when(recognitionUnitRepository.findById(1L)).thenReturn(Optional.of(recognitionUnit));
        when(edgeNodeService.makeRequest(any())).thenReturn("Puerta abierta correctamente");

        // ✅ Crear correctamente el recurso
        OpenDoorResource resource = new OpenDoorResource("ABC123-1");

        // ✅ Serializar automáticamente a JSON correcto
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resource);

        System.out.println("📦 Enviando JSON al endpoint: " + json);

        // Act & Assert
        mockMvc.perform(post("/api/v1/edge-nodes/open-door")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(result -> {
                    System.out.println("🔹 RESPONSE: " + result.getResponse().getContentAsString());
                    assertThat(result.getResponse().getContentAsString())
                            .isEqualTo("Puerta abierta correctamente");
                });
    }



    @Test
    void openDoor_ShouldReturnOk_WhenValidCode() {
        when(recognitionUnitRepository.findById(1L)).thenReturn(Optional.of(recognitionUnit));
        when(edgeNodeService.makeRequest(any())).thenReturn("Puerta abierta correctamente");

        OpenDoorResource resource = new OpenDoorResource("ABC123-1");
        ResponseEntity<String> response = doorController.openDoor(resource);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Puerta abierta correctamente");
    }

    @Test
    void openDoor_ShouldReturnForbidden_WhenInvalidQrCode() {
        when(recognitionUnitRepository.findById(1L)).thenReturn(Optional.of(recognitionUnit));

        OpenDoorResource resource = new OpenDoorResource("WRONGCODE-1");
        ResponseEntity<String> response = doorController.openDoor(resource);

        assertThat(response.getStatusCode().value()).isEqualTo(403);
        assertThat(response.getBody()).isEqualTo("Código inválido");
    }

    @Test
    void openDoor_ShouldReturnBadRequest_WhenInvalidFormat() {
        OpenDoorResource resource = new OpenDoorResource("INVALIDFORMAT");
        ResponseEntity<String> response = doorController.openDoor(resource);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("Formato inválido de full_code");
    }

    @Test
    void openDoor_ShouldThrow_WhenUnitNotFound() {
        when(recognitionUnitRepository.findById(1L)).thenReturn(Optional.empty());

        OpenDoorResource resource = new OpenDoorResource("ABC123-1");
        try {
            doorController.openDoor(resource);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Unidad no encontrada");
        }
    }
}
