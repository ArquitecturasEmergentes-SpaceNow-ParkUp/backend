package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.EdgeNodeQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories.RecognitionUnitRepository;

import java.util.Optional;

@Service
public class EdgeNodeQueryServiceImpl implements EdgeNodeQueryService {

    private final RecognitionUnitRepository recognitionUnitRepository;

    public EdgeNodeQueryServiceImpl(RecognitionUnitRepository recognitionUnitRepository) {
        this.recognitionUnitRepository = recognitionUnitRepository;
    }

    @Override
    public Optional<RecognitionUnit> findByIpAndPort(String ip, Integer port) {
        return recognitionUnitRepository.findByIpAddressAndPort(ip, port);
    }

    @Override
    public Optional<RecognitionUnit> findById(Long id) {
        return recognitionUnitRepository.findById(id);
    }
}