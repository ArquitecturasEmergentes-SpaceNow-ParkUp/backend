package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.application.internal.commandservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.aggregates.ExtraordinaryCase;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.CreateExtraordinaryCaseCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.LogFailedCaseAttemptCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.MarkCaseAsSurpassedCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.OpenBarrierForCaseCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.services.ExtraordinaryCaseCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.infrastructure.persistence.jpa.repositories.ExtraordinaryCaseRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor // Inyección de dependencias de Lombok
public class ExtraordinaryCaseCommandServiceImpl implements ExtraordinaryCaseCommandService {

    private final ExtraordinaryCaseRepository extraordinaryCaseRepository;
    // Asumiendo que tienes validadores, como en tu módulo 'affiliate'
    // private final CaseValidatorService caseValidatorService;

    @Override
    public Optional<ExtraordinaryCase> handle(CreateExtraordinaryCaseCommand command) {
        // Aquí irían validaciones
        var extraordinaryCase = new ExtraordinaryCase(command);
        extraordinaryCaseRepository.save(extraordinaryCase);
        return Optional.of(extraordinaryCase);
    }

    @Override
    public Optional<ExtraordinaryCase> handle(OpenBarrierForCaseCommand command) {
        return extraordinaryCaseRepository.findById(command.extraordinaryCaseId())
                .map(extraordinaryCase -> {
                    extraordinaryCase.openBarrier(); // Método del dominio
                    extraordinaryCaseRepository.save(extraordinaryCase);
                    return extraordinaryCase;
                });
    }

    @Override
    public Optional<ExtraordinaryCase> handle(MarkCaseAsSurpassedCommand command) {
        return extraordinaryCaseRepository.findById(command.extraordinaryCaseId())
                .map(extraordinaryCase -> {
                    extraordinaryCase.markAsSurpassed(); // Método del dominio
                    extraordinaryCaseRepository.save(extraordinaryCase);
                    return extraordinaryCase;
                });
    }

    @Override
    public Optional<ExtraordinaryCase> handle(LogFailedCaseAttemptCommand command) {
        return extraordinaryCaseRepository.findById(command.extraordinaryCaseId())
                .map(extraordinaryCase -> {
                    extraordinaryCase.markAsFailed(command.failureReason()); // Método del dominio
                    extraordinaryCaseRepository.save(extraordinaryCase);
                    return extraordinaryCase;
                });
    }
}