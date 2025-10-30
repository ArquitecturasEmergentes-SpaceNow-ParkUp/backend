package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * DocumentId value object
 * Represents a national identification document (DNI)
 */
@Embeddable
@Getter
public class DocumentId {

    @NotBlank
    private String documentNumber;

    protected DocumentId() {
    }

    /**
     * Constructor for DocumentId
     *
     * @param documentNumber The document identification number (DNI)
     */
    public DocumentId(String documentNumber) {
        if (documentNumber == null || documentNumber.isBlank()) {
            throw new IllegalArgumentException("Document number cannot be null or blank");
        }
        if (!documentNumber.matches("\\d{8}")) {
            throw new IllegalArgumentException("Document number must be 8 digits");
        }

        this.documentNumber = documentNumber;
    }

    @Override
    public String toString() {
        return documentNumber;
    }
}
