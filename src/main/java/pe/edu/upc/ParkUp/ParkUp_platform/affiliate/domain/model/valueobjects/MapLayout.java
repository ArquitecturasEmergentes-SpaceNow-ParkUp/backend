package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the layout of a parking lot map.
 * This is a Value Object / part of ParkingLotMap aggregate.
 */
@Embeddable
public class MapLayout {

    private int totalSpaces;
    private int disabilitySpaces;
    private String layoutJson;

    // Not a list of entities — only store simple identifiers or codes
    private List<String> spaceCodes = new ArrayList<>();

    public MapLayout() {}

    public MapLayout(int totalSpaces, int disabilitySpaces, String layoutJson) {
        this.totalSpaces = totalSpaces;
        this.disabilitySpaces = disabilitySpaces;
        this.layoutJson = layoutJson;
    }

    public int getTotalSpaces() { return totalSpaces; }
    public int getDisabilitySpaces() { return disabilitySpaces; }
    public String getLayoutJson() { return layoutJson; }

    public List<String> getSpaceCodes() {
        return Collections.unmodifiableList(spaceCodes);
    }

    public void addSpaceCode(String code) {
        this.spaceCodes.add(code);
    }
}