package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetAllRecognitionUnitsByAffiliateQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetRecognitionUnitByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services.RecognitionUnitQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.persistence.jpa.repositories.RecognitionUnitRepository;

import java.util.List;
import java.util.Optional;

/**
 * RecognitionUnitQueryServiceImpl
 * <p>
 *     This class implements the RecognitionUnitQueryService interface and provides
 *     the implementation for recognition unit query operations.
 * </p>
 */
@Service
public class RecognitionUnitQueryServiceImpl implements RecognitionUnitQueryService {

    private final RecognitionUnitRepository recognitionUnitRepository;

    public RecognitionUnitQueryServiceImpl(RecognitionUnitRepository recognitionUnitRepository) {
        this.recognitionUnitRepository = recognitionUnitRepository;
    }

    @Override
    public Optional<RecognitionUnit> handle(GetRecognitionUnitByIdQuery query) {
        return recognitionUnitRepository.findById(query.recognitionUnitId());
    }

    @Override
    public List<RecognitionUnit> handle(GetAllRecognitionUnitsByAffiliateQuery query) {
        return recognitionUnitRepository.findByAffiliateId(query.affiliateId());
    }

    @Override
    public List<RecognitionUnit> getAllRecognitionUnits() {
        return recognitionUnitRepository.findAll();
    }
}
