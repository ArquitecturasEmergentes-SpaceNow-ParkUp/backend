package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.aggregates;

import pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.entities.ParkingSpace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the layout of a parking lot map.
 * This is a Value Object / part of ParkingLotMap aggregate.
 */
public class MapLayout {
    private Long id;
    private int totalSpaces;
    private int disabilitySpaces;
    private String layoutJson; // structured data describing layout
    private final List<ParkingSpace> spaces = new ArrayList<>();

    public MapLayout(Long id, int totalSpaces, int disabilitySpaces, String layoutJson) {
        this.id = id;
        this.totalSpaces = totalSpaces;
        this.disabilitySpaces = disabilitySpaces;
        this.layoutJson = layoutJson;
    }

    public Long getId() { return id; }
    public int getTotalSpaces() { return totalSpaces; }
    public int getDisabilitySpaces() { return disabilitySpaces; }
    public String getLayoutJson() { return layoutJson; }
    public List<ParkingSpace> getSpaces() { return Collections.unmodifiableList(spaces); }

    public void addSpace(ParkingSpace s) { this.spaces.add(s); }
    public void removeSpace(ParkingSpace s) { this.spaces.remove(s); }

    public void updateLayoutJson(String newJson) { this.layoutJson = newJson; }
}