package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * FullName value object
 * Represents a person's full name
 */
@Embeddable
@Getter
public class FullName {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    protected FullName() {
    }

    /**
     * Constructor for FullName
     *
     * @param firstName The first name(s)
     * @param lastName  The last name(s)
     */
    public FullName(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or blank");
        }

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    /**
     * Gets the complete full name
     *
     * @return Full name as "firstName lastName"
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
