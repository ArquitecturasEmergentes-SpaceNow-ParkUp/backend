package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingLotMap;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.ParkingLotId;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate root for ParkingLot.
 */
@Entity
@Table(name = "parking_lots")
@Getter
@Setter
@NoArgsConstructor
public class ParkingLot extends AuditableAbstractAggregateRoot<ParkingLot> {

    private String name;
    private String address;

    // Evita final, Hibernate necesita proxys en listas
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id")
    private List<ParkingLotMap> maps = new ArrayList<>();

    public ParkingLot(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public ParkingLotId getParkingLotId() {
        return new ParkingLotId(super.getId());
    }

    public List<ParkingLotMap> getMaps() {
        return Collections.unmodifiableList(maps);
    }

    public void addMap(ParkingLotMap map) {
        this.maps.add(map);
    }
}