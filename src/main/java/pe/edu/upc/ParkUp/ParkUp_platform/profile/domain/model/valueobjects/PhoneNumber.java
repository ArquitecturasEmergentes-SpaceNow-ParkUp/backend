package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * PhoneNumber value object
 * Represents a phone number with country code
 */
@Embeddable
@Getter
public class PhoneNumber {

    @NotBlank
    private String countryCode;

    @NotBlank
    private String number;

    protected PhoneNumber() {
    }

    /**
     * Constructor for PhoneNumber
     *
     * @param countryCode The country code (e.g., "+51", "+1")
     * @param number      The phone number without country code
     */
    public PhoneNumber(String countryCode, String number) {
        if (countryCode == null || countryCode.isBlank()) {
            throw new IllegalArgumentException("Country code cannot be null or blank");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or blank");
        }
        if (!countryCode.startsWith("+")) {
            throw new IllegalArgumentException("Country code must start with +");
        }
        if (!number.matches("\\d+")) {
            throw new IllegalArgumentException("Phone number must contain only digits");
        }

        this.countryCode = countryCode;
        this.number = number;
    }

    /**
     * Gets the full phone number with country code
     *
     * @return Full phone number
     */
    public String getFullPhoneNumber() {
        return countryCode + number;
    }

    @Override
    public String toString() {
        return getFullPhoneNumber();
    }
}
