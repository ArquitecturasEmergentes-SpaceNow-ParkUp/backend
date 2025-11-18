package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.queries.GetParkingLotByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingLotQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingSpaceQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources.ParkingSlotResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Parking Slots Controller", description = "Endpoints to fetch parking slots per parking lot")
@RequestMapping("/api/v1/parking-lots")
public class ParkingSlotsController {

    private final ParkingLotQueryService parkingLotQueryService;
    private final ParkingSpaceQueryService spaceQueryService;

    public ParkingSlotsController(ParkingLotQueryService parkingLotQueryService, ParkingSpaceQueryService spaceQueryService) {
        this.parkingLotQueryService = parkingLotQueryService;
        this.spaceQueryService = spaceQueryService;
    }

    @GetMapping("/{parkingLotId}/slots")
    public ResponseEntity<List<ParkingSlotResource>> getSlotsByParkingLotId(@PathVariable("parkingLotId") Long parkingLotId) {
        var maybeLot = parkingLotQueryService.handle(new GetParkingLotByIdQuery(parkingLotId));
        if (maybeLot.isEmpty()) return ResponseEntity.notFound().build();

        var lot = maybeLot.get();
        // pick the latest map from the parking lot
        var maps = lot.getMaps();
        if (maps == null || maps.isEmpty()) return ResponseEntity.ok(List.of());

        var last = maps.get(maps.size() - 1);
        var spaces = spaceQueryService.getByMapId(last.getId());

        List<ParkingSlotResource> resources = spaces.stream().map(space -> {
            ParkingSlotResource r = new ParkingSlotResource(
                space.getId(),
                space.getCode(),
                space.isDisability(),
                space.getStatus().name(),
                lot.getId()
            );
            r.setSlotNumber(space.getCode());
            return r;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }
}
