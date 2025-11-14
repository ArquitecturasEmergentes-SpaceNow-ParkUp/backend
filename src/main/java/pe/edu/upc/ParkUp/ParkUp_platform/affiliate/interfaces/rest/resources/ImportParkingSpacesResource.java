package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ImportParkingSpacesResource {
    @NotNull
    private Long mapId;
    @NotNull
    private List<Item> items;

    public static class Item {
        private String code;
        private boolean disability;

        public Item() {}
        public Item(String code, boolean disability) { this.code = code; this.disability = disability; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public boolean isDisability() { return disability; }
        public void setDisability(boolean disability) { this.disability = disability; }
    }

    public ImportParkingSpacesResource() {}
    public ImportParkingSpacesResource(Long mapId, List<Item> items) { this.mapId = mapId; this.items = items; }
    public Long getMapId() { return mapId; }
    public void setMapId(Long mapId) { this.mapId = mapId; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}

