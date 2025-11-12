package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.acl.outbouded;

import org.springframework.stereotype.Service;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.queries.GetParkingLotByIdQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.services.ParkingLotQueryService;

@Service
public class AffiliateParkingLotACL {

    private final ParkingLotQueryService affiliateQueryService;

    public AffiliateParkingLotACL(ParkingLotQueryService affiliateQueryService) {
        this.affiliateQueryService = affiliateQueryService;
    }

    public AffiliateParkingLotReference getReference(Long affiliateId) {
        var maybeLot = affiliateQueryService.handle(new GetParkingLotByIdQuery(affiliateId));

        return maybeLot
                .map(lot -> new AffiliateParkingLotReference(
                        lot.getId(),
                        lot.getName()
                ))
                .orElseThrow(() -> new RuntimeException("Affiliate parking lot not found"));
    }
}
