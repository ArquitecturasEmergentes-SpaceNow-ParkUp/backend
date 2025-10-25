package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.transform;

import org.springframework.stereotype.Component;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.EdgeNodeQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeRegistrationResource;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.interfaces.rest.resources.EdgeNodeResource;

import java.util.Optional;

@Component
public class EdgeNodeRegistrationToEdgeNodeResourceMapper {

    private final EdgeNodeQueryService edgeNodeQueryService;

    public EdgeNodeRegistrationToEdgeNodeResourceMapper(EdgeNodeQueryService edgeNodeQueryService) {
        this.edgeNodeQueryService = edgeNodeQueryService;
    }

    /**
     * Convierte el resource mínimo (ip, port) en un EdgeNodeResource completo
     * buscando en la base de datos por ip y port.
     *
     * Lanza RuntimeException si no existe la unidad para esa ip/port.
     */
    public EdgeNodeResource toEdgeNodeResource(EdgeNodeRegistrationResource registration) {
        Optional<RecognitionUnit> found = edgeNodeQueryService.findByIpAndPort(registration.getIp(), registration.getPort());

        RecognitionUnit unit = found.orElseThrow(() ->
                new RuntimeException("No se encontró RecognitionUnit con ip=" + registration.getIp()
                        + " y port=" + registration.getPort()));

        return new EdgeNodeResource(
                registration.getIp(),
                registration.getPort(),
                unit.getIdentifier(),
                unit.getAffiliateId(),
                unit.getParkingLotId()
        );
    }
}
