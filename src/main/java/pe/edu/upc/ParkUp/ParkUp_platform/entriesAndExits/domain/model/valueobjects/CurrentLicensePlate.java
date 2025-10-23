package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * CurrentLicensePlate value object
 * <p>
 *     This value object captures the detected license plate information
 *     including the plate number, timestamp, and recognition unit ID.
 * </p>
 */
@Embeddable
@Getter
@Setter
public class CurrentLicensePlate {

    @NotBlank
    @Column(name = "plate", nullable = false, length = 20)
    private String plate;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @NotNull
    @Column(name = "recognition_unit_id", nullable = false)
    private Long recognitionUnitId;

    public CurrentLicensePlate() {
        this.timestamp = LocalDateTime.now();
    }

    public CurrentLicensePlate(String plate, Long recognitionUnitId) {
        this();
        this.plate = plate;
        this.recognitionUnitId = recognitionUnitId;
    }

    /**
     * Create a new CurrentLicensePlate with current timestamp
     * @param plate the license plate number
     * @param recognitionUnitId the recognition unit ID
     * @return new CurrentLicensePlate instance
     */
    public static CurrentLicensePlate of(String plate, Long recognitionUnitId) {
        return new CurrentLicensePlate(plate, recognitionUnitId);
    }

    /**
     * Check if the license plate is valid
     * @return true if plate is not null and not empty, false otherwise
     */
    public boolean isValid() {
        return this.plate != null && !this.plate.trim().isEmpty();
    }

    /**
     * Get the license plate in uppercase
     * @return the license plate in uppercase
     */
    public String getPlateUpperCase() {
        return this.plate != null ? this.plate.toUpperCase() : null;
    }

    /**
     * Check if the detection is recent (within last 5 minutes)
     * @return true if detection is recent, false otherwise
     */
    public boolean isRecent() {
        if (this.timestamp == null) {
            return false;
        }
        return LocalDateTime.now().minusMinutes(5).isBefore(this.timestamp);
    }
}
