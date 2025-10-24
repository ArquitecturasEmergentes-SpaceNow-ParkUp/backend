package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.services;

import pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities.RecognitionUnit;

import java.util.Optional;

public interface EdgeNodeQueryService {
    Optional<RecognitionUnit> findByIpAndPort(String ip, Integer port);
    Optional<RecognitionUnit> findById(Long id);

}