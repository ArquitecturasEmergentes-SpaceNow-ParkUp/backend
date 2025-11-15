package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.commands;

import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;

public record UpdateParkingSpaceStatusCommand(Long spaceId, ParkingSpace.SpaceStatus status) {}

