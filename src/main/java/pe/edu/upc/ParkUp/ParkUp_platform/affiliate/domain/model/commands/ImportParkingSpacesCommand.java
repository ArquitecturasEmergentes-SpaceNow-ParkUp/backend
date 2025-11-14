package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands;

import java.util.List;

public record ImportParkingSpacesCommand(Long mapId, List<SpaceItem> items) {
    public record SpaceItem(String code, boolean disability) {}
}

