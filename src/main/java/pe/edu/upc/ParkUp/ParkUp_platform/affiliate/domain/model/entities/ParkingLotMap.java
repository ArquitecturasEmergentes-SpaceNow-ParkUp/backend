package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.MapLayout;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.MapId;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.entities.AuditableModel;

/**
 * Represents a map attached to a ParkingLot.
 */
@Entity
@Table(name = "parking_lot_maps")
@Getter
@Setter
@NoArgsConstructor
public class ParkingLotMap extends AuditableModel {

    public enum MapStatus { DRAFT, SUBMITTED, ACCEPTED, REJECTED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MapLayout layout;

    @Enumerated(EnumType.STRING)
    private MapStatus status = MapStatus.DRAFT;

    public ParkingLotMap(MapLayout layout) {
        this.layout = layout;
    }

    public MapId getMapId() {
        return new MapId(this.id);
    }

    public void submit() { this.status = MapStatus.SUBMITTED; }
    public void accept() { this.status = MapStatus.ACCEPTED; }
    public void reject() { this.status = MapStatus.REJECTED; }
    public void editLayout(MapLayout newLayout) { this.layout = newLayout; }
}