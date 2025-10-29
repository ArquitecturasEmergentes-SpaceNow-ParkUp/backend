package pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.entities.CreditCard;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.queries.GetAllCreditCardsByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.queries.GetCreditCardByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services.CreditCardCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services.CreditCardQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources.CreateCreditCardResource;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources.CreditCardResource;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources.DeleteCreditCardResource;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.transform.CreateCreditCardFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.transform.CreditCardResourceFromEntityAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.transform.DeleteCreditCardFromResourceAssembler;

import java.util.List;

@Tag(name = "Credit Cards", description = "Credit Card Management Endpoints")
@RestController
@RequestMapping("/api/v1/credit-cards")
public class CreditCardController {

    @Autowired
    private CreditCardCommandService commandService;

    @Autowired
    private CreditCardQueryService queryService;

    @PostMapping
    public ResponseEntity<CreditCardResource> createCreditCard(@RequestBody CreateCreditCardResource resource) {
        var command = CreateCreditCardFromResourceAssembler.toCommandFromResource(resource);
        CreditCard creditCard = commandService.handle(command);
        var creditCardResource = CreditCardResourceFromEntityAssembler.toResourceFromEntity(creditCard);
        return ResponseEntity.ok(creditCardResource);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCreditCard(@RequestBody DeleteCreditCardResource resource) {
        var command = DeleteCreditCardFromResourceAssembler.toCommandFromResource(resource);
        commandService.handle(command);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{creditCardId}")
    public ResponseEntity<CreditCardResource> getCreditCardById(@PathVariable Long creditCardId) {
        var query = new GetCreditCardByIdQuery(creditCardId);
        var creditCard = queryService.handle(query);
        var resource = CreditCardResourceFromEntityAssembler.toResourceFromEntity(creditCard);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CreditCardResource>> getAllCreditCardsByUserId(@PathVariable Long userId) {
        var query = new GetAllCreditCardsByUserIdQuery(userId);
        var cards = queryService.handle(query);
        var resources = cards.stream()
                .map(CreditCardResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}

