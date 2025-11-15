package pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.LogFailedCaseAttemptCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.MarkCaseAsSurpassedCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.commands.OpenBarrierForCaseCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.queries.GetAllExtraordinaryCasesQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.model.queries.GetExtraordinaryCaseByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.services.ExtraordinaryCaseCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.domain.services.ExtraordinaryCaseQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.resources.CreateExtraordinaryCaseResource;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.resources.ExtraordinaryCaseResource;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.resources.LogFailedCaseResource;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.transform.CreateExtraordinaryCaseCommandFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.extraordinariesCases.interfaces.rest.transform.ExtraordinaryCaseResourceFromEntityAssembler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/extraordinary-cases")
@Tag(name = "Extraordinary Cases")
@RequiredArgsConstructor
public class ExtraordinaryCaseController {

    private final ExtraordinaryCaseCommandService extraordinaryCaseCommandService;
    private final ExtraordinaryCaseQueryService extraordinaryCaseQueryService;

    // POST /api/v1/extraordinary-cases
    @PostMapping
    public ResponseEntity<ExtraordinaryCaseResource> createExtraordinaryCase(@RequestBody CreateExtraordinaryCaseResource resource) {

        // --- PASO DE DIAGNÓSTICO ---
        // Imprime el resource para ver si Jackson lo está populando correctamente
        System.out.println("--- DATOS RECIBIDOS EN EL RESOURCE: " + resource + " ---");

        var createCaseCommand = CreateExtraordinaryCaseCommandFromResourceAssembler.toCommandFromResource(resource);
        System.out.println("--- DATOS RECIBIDOS EN EL COMANDO: " + createCaseCommand.toString() + " ---");

        var extraordinaryCase = extraordinaryCaseCommandService.handle(createCaseCommand)
                .orElseThrow(() -> new RuntimeException("Error creating extraordinary case"));

        return new ResponseEntity<>(ExtraordinaryCaseResourceFromEntityAssembler.toResourceFromEntity(extraordinaryCase), HttpStatus.CREATED);
    }
    // GET /api/v1/extraordinary-cases
    @GetMapping
    public ResponseEntity<List<ExtraordinaryCaseResource>> getAllExtraordinaryCases() {
        var getAllCasesQuery = new GetAllExtraordinaryCasesQuery();
        var cases = extraordinaryCaseQueryService.handle(getAllCasesQuery);
        var caseResources = cases.stream()
                .map(ExtraordinaryCaseResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(caseResources);
    }

    // GET /api/v1/extraordinary-cases/{caseId}
    @GetMapping("/{caseId}")
    public ResponseEntity<ExtraordinaryCaseResource> getCaseById(@PathVariable Long caseId) {
        var getCaseByIdQuery = new GetExtraordinaryCaseByIdQuery(caseId);
        var extraordinaryCase = extraordinaryCaseQueryService.handle(getCaseByIdQuery)
                .orElseThrow(() -> new RuntimeException("Case not found"));
        return ResponseEntity.ok(ExtraordinaryCaseResourceFromEntityAssembler.toResourceFromEntity(extraordinaryCase));
    }

    // PATCH /api/v1/extraordinary-cases/{caseId}/open-barrier
    @PatchMapping("/{caseId}/open-barrier")
    public ResponseEntity<ExtraordinaryCaseResource> openBarrier(@PathVariable Long caseId) {
        var openBarrierCommand = new OpenBarrierForCaseCommand(caseId);
        var extraordinaryCase = extraordinaryCaseCommandService.handle(openBarrierCommand)
                .orElseThrow(() -> new RuntimeException("Case not found or cannot be opened"));
        return ResponseEntity.ok(ExtraordinaryCaseResourceFromEntityAssembler.toResourceFromEntity(extraordinaryCase));
    }

    // PATCH /api/v1/extraordinary-cases/{caseId}/surpass
    @PatchMapping("/{caseId}/surpass")
    public ResponseEntity<ExtraordinaryCaseResource> markAsSurpassed(@PathVariable Long caseId) {
        var markAsSurpassedCommand = new MarkCaseAsSurpassedCommand(caseId);
        var extraordinaryCase = extraordinaryCaseCommandService.handle(markAsSurpassedCommand)
                .orElseThrow(() -> new RuntimeException("Case not found or cannot be marked as surpassed"));
        return ResponseEntity.ok(ExtraordinaryCaseResourceFromEntityAssembler.toResourceFromEntity(extraordinaryCase));
    }

    // PATCH /api/v1/extraordinary-cases/{caseId}/fail
    @PatchMapping("/{caseId}/fail")
    public ResponseEntity<ExtraordinaryCaseResource> logFailure(@PathVariable Long caseId, @RequestBody LogFailedCaseResource resource) {
        var logFailureCommand = new LogFailedCaseAttemptCommand(caseId, resource.failureReason());
        var extraordinaryCase = extraordinaryCaseCommandService.handle(logFailureCommand)
                .orElseThrow(() -> new RuntimeException("Case not found"));
        return ResponseEntity.ok(ExtraordinaryCaseResourceFromEntityAssembler.toResourceFromEntity(extraordinaryCase));
    }
}