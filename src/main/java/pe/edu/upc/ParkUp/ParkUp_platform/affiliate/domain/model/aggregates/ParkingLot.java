package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates;

import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.ParkingLotId;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.aggregates.Ticket;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Aggregate root for ParkingLot.
 */
public class ParkingLot extends AuditableAbstractAggregateRoot<ParkingLot> {

    private String name;
    private String address;
    private final List<ParkingLotMap> maps = new ArrayList<>();

    // ✅ Devuelve el value object usando el id Long heredado
    public ParkingLotId getParkingLotId() {
        return new ParkingLotId(super.getId());
    }

    // ✅ Ya no redefinas getId() ni getCreatedAt()
    public String getName() { return name; }
    public String getAddress() { return address; }
    public List<ParkingLotMap> getMaps() { return maps; }

    public ParkingLot(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addMap(ParkingLotMap map) {
        this.maps.add(map);
    }
}