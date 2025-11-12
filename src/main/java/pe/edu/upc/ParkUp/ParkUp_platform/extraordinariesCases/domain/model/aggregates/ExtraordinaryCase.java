package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.CreateExtraordinaryCaseCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.valueobjects.CaseStatus;

import java.util.Date;

@Entity
@Getter
public class ExtraordinaryCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Podrías usar un ValueObject aquí si ya tienes uno
    private Long parkingLotId;

    // Podrías usar un ValueObject aquí
    private Long recognitionUnitId;

    private String plateNumber;

    private String caseReason;

    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public ExtraordinaryCase() {
        // Para JPA
    }

    public ExtraordinaryCase(CreateExtraordinaryCaseCommand command) {
        this.parkingLotId = command.parkingLotId();
        this.recognitionUnitId = command.recognitionUnitId();
        this.plateNumber = command.plateNumber();
        this.caseReason = command.caseReason();
        this.status = CaseStatus.ENTERED; // Estado inicial según el diagrama
        this.createdAt = new Date();
    }

    // Método para la acción del Admin "Open recognition-unit barrier"
    public void openBarrier() {
        if (this.status == CaseStatus.ENTERED) {
            this.status = CaseStatus.OPENED;
            // Aquí se podría publicar un evento (Domain Event)
            // addEvent(new BarrierOpenedForCaseEvent(this.id, this.recognitionUnitId));
        } else {
            throw new IllegalStateException("Barrier can only be opened for cases in ENTERED state.");
        }
    }

    // Método para "Barrier surpassed"
    public void markAsSurpassed() {
        if (this.status == CaseStatus.OPENED) {
            this.status = CaseStatus.SURPASSED;
        } else {
            throw new IllegalStateException("Barrier can only be marked as surpassed if it was OPENED.");
        }
    }

    // Método para "Car plate... unregistered" o "No vehicle"
    public void markAsFailed(String failureReason) {
        this.status = CaseStatus.FAILED;
        this.caseReason = this.caseReason + " | Failure: " + failureReason;
    }
}