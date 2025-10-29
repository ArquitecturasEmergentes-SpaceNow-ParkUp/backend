package pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.model.queries.GetAllPaymentsByUserIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services.PaymentCommandService;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.domain.services.PaymentQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources.CreatePaymentResource;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.resources.PaymentResource;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.transform.CreatePaymentFromResourceAssembler;
import pe.edu.upc.ParkUp.ParkUp_platform.payments.interfaces.rest.transform.PaymentResourceFromEntityAssembler;

import java.util.List;

@Tag(name = "Payment", description = "Payment Management Endpoints")
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentCommandService commandService;

    @Autowired
    private PaymentQueryService queryService;

    @PostMapping
    public ResponseEntity<PaymentResource> createPayment(@RequestBody CreatePaymentResource resource) {
        var command = CreatePaymentFromResourceAssembler.toCommandFromResource(resource);
        var payment = commandService.handle(command);
        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(payment);
        return ResponseEntity.ok(paymentResource);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResource>> getPaymentsByUserId(@PathVariable Long userId) {
        var query = new GetAllPaymentsByUserIdQuery(userId);
        var payments = queryService.handle(query);
        var resources = payments.stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
