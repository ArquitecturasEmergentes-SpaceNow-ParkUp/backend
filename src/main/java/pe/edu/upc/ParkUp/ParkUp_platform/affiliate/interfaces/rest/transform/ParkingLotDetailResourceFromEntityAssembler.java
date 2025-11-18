package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates.ParkingLot;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.ParkingLotDetailResource;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceQueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ParkingLotDetailResourceFromEntityAssembler {

    private final ParkingSpaceQueryService parkingSpaceQueryService;
    private final ObjectMapper objectMapper;

    public ParkingLotDetailResourceFromEntityAssembler(ParkingSpaceQueryService parkingSpaceQueryService, ObjectMapper objectMapper) {
        this.parkingSpaceQueryService = parkingSpaceQueryService;
        this.objectMapper = objectMapper;
    }

    public ParkingLotDetailResource toResource(ParkingLot lot) {
        List<ParkingLotDetailResource.ParkingLotMapResource> maps = new ArrayList<>();
        var mapEntities = lot.getMaps();
        for (var m : mapEntities) {
            String layout = m.getLayout().getLayoutJson();
            String normalized = layout;
            // If layout looks numeric or empty, try fallback building from stored spaces or codes
            if (layout == null || layout.isBlank() || layout.matches("^\\d+$")) {
                normalized = normalizeLayoutFromSpaces(m.getId());
            } else {
                // try to parse, if invalid fall back
                try {
                    objectMapper.readTree(layout);
                } catch (Exception e) {
                    normalized = normalizeLayoutFromSpaces(m.getId());
                }
            }

            maps.add(new ParkingLotDetailResource.ParkingLotMapResource(
                    m.getId(), lot.getId(), normalized, m.getLayout().getTotalSpaces(), m.getLayout().getDisabilitySpaces(), m.getStatus().name()
            ));
        }

        return new ParkingLotDetailResource(lot.getId(), lot.getName(), lot.getAddress(), maps);
    }

    private String normalizeLayoutFromSpaces(Long mapId) {
        var spaces = parkingSpaceQueryService.getByMapId(mapId);
        // Group by row letter (prefix before digits), e.g., A1 -> row 'A'
        Map<String, List<String>> grouped = spaces.stream()
                .map(s -> s.getCode())
                .collect(Collectors.groupingBy(code -> code.replaceAll("\\d+$", "")));

        var rows = new ArrayList<Map<String, Object>>();
        for (var entry : grouped.entrySet()) {
            var ids = entry.getValue();
            var rowObj = Map.of("row", entry.getKey(), "slots", List.of(Map.of("ids", ids, "gap", false)));
            rows.add(rowObj);
        }

        try {
            return objectMapper.writeValueAsString(rows);
        } catch (Exception e) {
            return "[]";
        }
    }
}
