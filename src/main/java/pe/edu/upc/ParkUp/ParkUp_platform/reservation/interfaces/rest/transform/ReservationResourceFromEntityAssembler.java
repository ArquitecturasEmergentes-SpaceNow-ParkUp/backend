package pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.rest.transform;

import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.interfaces.rest.resources.ReservationResource;

/**
 * Assembler to convert Reservation entity to ReservationResource
 */
public class ReservationResourceFromEntityAssembler {

    /**
     * Converts a Reservation entity to a ReservationResource
     *
     * @param entity The Reservation entity
     * @return The ReservationResource
     */
    public static ReservationResource toResourceFromEntity(Reservation entity) {
        return new ReservationResource(
                entity.getId(),
                entity.getUserId(),
                entity.getParkingSlotId().getParkingLotId(),
                entity.getStatus().name(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getActualStartTime(),
                entity.getActualEndTime(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
