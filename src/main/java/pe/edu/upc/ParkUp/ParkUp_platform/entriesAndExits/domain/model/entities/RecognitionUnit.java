package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.entities.AuditableModel;

/**
 * RecognitionUnit entity
 * <p>
 *     This entity represents the hardware unit (smart barrier, camera) 
 *     located at parking entrances and exits.
 * </p>
 */
@Entity
@Table(name = "recognition_units")
@Getter
@Setter
public class RecognitionUnit extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "identifier", nullable = false, unique = true, length = 100)
    private String identifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "location", nullable = false, length = 20)
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status;

    @NotNull
    @Column(name = "affiliate_id", nullable = false)
    private Long affiliateId;

    @NotNull
    @Column(name = "parking_lot_id", nullable = false)
    private Long parkingLotId;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "port")
    private Integer port;

    @Column(name = "server_code", length = 50)
    private String serverCode;

    @Column(name = "qr_code", length = 500)
    private String qrCode;

    public RecognitionUnit() {
        this.status = Status.ACTIVE;
    }

    public RecognitionUnit(String identifier, Location location, Long affiliateId, Long parkingLotId) {
        this();
        this.identifier = identifier;
        this.location = location;
        this.affiliateId = affiliateId;
        this.parkingLotId = parkingLotId;
    }

    public RecognitionUnit(String identifier, Location location, Long affiliateId, Long parkingLotId, 
                          String ipAddress, Integer port) {
        this(identifier, location, affiliateId, parkingLotId);
        this.ipAddress = ipAddress;
        this.port = port;
    }

    /**
     * Activate the recognition unit
     * @return the recognition unit with updated status
     */
    public RecognitionUnit activate() {
        this.status = Status.ACTIVE;
        return this;
    }

    /**
     * Deactivate the recognition unit
     * @return the recognition unit with updated status
     */
    public RecognitionUnit deactivate() {
        this.status = Status.INACTIVE;
        return this;
    }

    /**
     * Put the recognition unit in maintenance mode
     * @return the recognition unit with updated status
     */
    public RecognitionUnit putInMaintenance() {
        this.status = Status.MAINTENANCE;
        return this;
    }

    /**
     * Check if the recognition unit is active
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return this.status == Status.ACTIVE;
    }

    /**
     * Check if the recognition unit is at entrance
     * @return true if at entrance, false otherwise
     */
    public boolean isAtEntrance() {
        return this.location == Location.ENTRANCE;
    }

    /**
     * Check if the recognition unit is at exit
     * @return true if at exit, false otherwise
     */
    public boolean isAtExit() {
        return this.location == Location.EXIT;
    }

    /**
     * Generate full code for QR generation
     * @return the full code in format "${serverCode}-${id}"
     */
    public String getFullCode() {
        if (this.serverCode == null || this.id == null) {
            return null;
        }
        return this.serverCode + "-" + this.id;
    }

    /**
     * Set server code and generate QR code
     * @param serverCode the server assigned code
     * @return the recognition unit with updated server code
     */
    public RecognitionUnit setServerCode(String serverCode) {
        this.serverCode = serverCode;
        this.qrCode = this.getFullCode();
        return this;
    }

    /**
     * Check if the recognition unit has network configuration
     * @return true if IP and port are set, false otherwise
     */
    public boolean hasNetworkConfig() {
        return this.ipAddress != null && !this.ipAddress.isEmpty() && 
               this.port != null && this.port > 0;
    }

    /**
     * Get the full URL for communication with this recognition unit
     * @return the full URL or null if network config is incomplete
     */
    public String getFullUrl() {
        if (!hasNetworkConfig()) {
            return null;
        }
        return "http://" + this.ipAddress + ":" + this.port;
    }

    /**
     * Location enumeration
     */
    public enum Location {
        ENTRANCE, EXIT
    }

    /**
     * Status enumeration
     */
    public enum Status {
        ACTIVE, INACTIVE, MAINTENANCE
    }
}
