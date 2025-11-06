package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates;


import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects.MapId;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Represents a map attached to a ParkingLot.
 */
public class ParkingLotMap extends AuditableAbstractAggregateRoot<ParkingLotMap> {
    public enum MapStatus { DRAFT, SUBMITTED, ACCEPTED, REJECTED }

    private final MapId id;
    private MapLayout layout;
    private MapStatus status = MapStatus.DRAFT;

    public ParkingLotMap(MapId id, MapLayout layout) {
        this.id = id;
        this.layout = layout;
    }

    public MapId getMapId() { return id; }
    public MapLayout getLayout() { return layout; }
    public MapStatus getStatus() { return status; }

    public void submit() { this.status = MapStatus.SUBMITTED; }
    public void accept() { this.status = MapStatus.ACCEPTED; }
    public void reject() { this.status = MapStatus.REJECTED; }

    public void editLayout(MapLayout newLayout) { this.layout = newLayout; }
}
