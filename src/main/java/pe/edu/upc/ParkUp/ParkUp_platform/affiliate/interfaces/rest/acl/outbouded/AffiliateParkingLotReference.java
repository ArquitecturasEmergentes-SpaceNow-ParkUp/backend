package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.acl.outbouded;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AffiliateParkingLotReference {
    private final Long id;
    private final String name;


    public AffiliateParkingLotReference(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // getters, equals, hashcode
}
