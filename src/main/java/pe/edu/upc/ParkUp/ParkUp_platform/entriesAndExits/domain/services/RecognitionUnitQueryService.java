package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetAllRecognitionUnitsByAffiliateQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.queries.GetRecognitionUnitByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * RecognitionUnitQueryService
 * <p>
 *     This service defines the query operations for RecognitionUnit entity.
 * </p>
 */
public interface RecognitionUnitQueryService {
    
    /**
     * Get recognition unit by ID
     * @param query the get recognition unit by ID query
     * @return optional recognition unit if found
     */
    Optional<RecognitionUnit> handle(GetRecognitionUnitByIdQuery query);
    
    /**
     * Get all recognition units by affiliate
     * @param query the get all recognition units by affiliate query
     * @return list of recognition units
     */
    List<RecognitionUnit> handle(GetAllRecognitionUnitsByAffiliateQuery query);
    
    /**
     * Get all recognition units
     * @return list of all recognition units
     */
    List<RecognitionUnit> getAllRecognitionUnits();
}
