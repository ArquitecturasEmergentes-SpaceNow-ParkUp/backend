package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.aggregates.ExtraordinaryCase;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.CreateExtraordinaryCaseCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.LogFailedCaseAttemptCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.MarkCaseAsSurpassedCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.OpenBarrierForCaseCommand;

import java.util.Optional;

public interface ExtraordinaryCaseCommandService {
    Optional<ExtraordinaryCase> handle(CreateExtraordinaryCaseCommand command);
    Optional<ExtraordinaryCase> handle(OpenBarrierForCaseCommand command);
    Optional<ExtraordinaryCase> handle(MarkCaseAsSurpassedCommand command);
    Optional<ExtraordinaryCase> handle(LogFailedCaseAttemptCommand command);
}