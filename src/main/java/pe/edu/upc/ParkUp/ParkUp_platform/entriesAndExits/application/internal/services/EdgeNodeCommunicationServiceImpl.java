package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.application.internal.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.commands.MakeEdgeNodeRequestCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.EdgeNodeCommunicationService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories.RecognitionUnitRepository;

/**
 * EdgeNodeCommunicationServiceImpl
 * <p>
 *     This service implementation handles HTTP communication with Edge Nodes.
 * </p>
 */
@Service
public class EdgeNodeCommunicationServiceImpl implements EdgeNodeCommunicationService {

    private final RestTemplate restTemplate;
    private final RecognitionUnitRepository recognitionUnitRepository;

    public EdgeNodeCommunicationServiceImpl(RestTemplate restTemplate,
                                          RecognitionUnitRepository recognitionUnitRepository) {
        this.restTemplate = restTemplate;
        this.recognitionUnitRepository = recognitionUnitRepository;
    }

    @Override
    public String makeRequest(MakeEdgeNodeRequestCommand command) {
        RecognitionUnit recognitionUnit = recognitionUnitRepository.findById(command.recognitionUnitId())
                .orElseThrow(() -> new IllegalArgumentException("Recognition unit not found with ID: " + command.recognitionUnitId()));

        if (!recognitionUnit.hasNetworkConfig()) {
            throw new IllegalArgumentException("Recognition unit does not have network configuration");
        }

        String fullUrl = buildFullUrl(recognitionUnit, command.endpoint());
        HttpEntity<String> entity = createHttpEntity(command.requestBody());

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    fullUrl,
                    command.method(),
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to communicate with Edge Node: " + e.getMessage(), e);
        }
    }

    private String buildFullUrl(RecognitionUnit recognitionUnit, String endpoint) {
        return recognitionUnit.getFullUrl() + 
               (endpoint.startsWith("/") ? endpoint : "/" + endpoint);
    }

    private HttpEntity<String> createHttpEntity(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }
}
