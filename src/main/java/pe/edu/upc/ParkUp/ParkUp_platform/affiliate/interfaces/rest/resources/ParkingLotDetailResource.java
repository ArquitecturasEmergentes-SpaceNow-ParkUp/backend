package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources;

import java.util.List;

public record ParkingLotDetailResource(
        Long id,
        String name,
        String address,
        List<ParkingLotMapResource> maps
) {
    public record ParkingLotMapResource(Long id, Long parkingLotId, String layoutJson, int totalSpaces, int disabilitySpaces, String status) {}
}
