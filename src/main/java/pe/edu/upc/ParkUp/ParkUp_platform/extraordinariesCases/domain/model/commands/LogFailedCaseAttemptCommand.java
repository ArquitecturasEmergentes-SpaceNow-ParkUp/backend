package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands;

public record LogFailedCaseAttemptCommand(Long extraordinaryCaseId, String failureReason) {
}