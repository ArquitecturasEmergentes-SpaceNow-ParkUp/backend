package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.entities.AuditableModel;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingLotMap;
@Entity
@Table(name = "parking_spaces")
@Getter
@Setter
public class ParkingSpace extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private boolean disability;
    private SpaceStatus status = SpaceStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_map_id")
    private ParkingLotMap map;

    public ParkingSpace() {

    }

    public enum SpaceStatus { AVAILABLE, OCCUPIED, RESERVED }

    public ParkingSpace(String code, boolean disability, ParkingLotMap map) {
        this.code = code;
        this.disability = disability;
        this.map = map;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public boolean isDisability() { return disability; }
    public SpaceStatus getStatus() { return status; }

    public void occupy() { this.status = SpaceStatus.OCCUPIED; }
    public void release() { this.status = SpaceStatus.AVAILABLE; }
    public void reserve() { this.status = SpaceStatus.RESERVED; }
}
