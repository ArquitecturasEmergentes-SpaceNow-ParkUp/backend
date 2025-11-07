package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands;

import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.MapLayout;

public record CreateParkingLotCommand(
        String name,
        String address,
        MapLayout initialMap // can be null
) {}