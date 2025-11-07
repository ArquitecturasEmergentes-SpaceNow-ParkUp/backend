package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class CreateParkingLotResource {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    // puede ser null si no se envía mapa inicial
    private MapLayoutResource initialMap;

    public CreateParkingLotResource() {}

    public CreateParkingLotResource(String name, String address, MapLayoutResource initialMap) {
        this.name = name;
        this.address = address;
        this.initialMap = initialMap;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public MapLayoutResource getInitialMap() { return initialMap; }
    public void setInitialMap(MapLayoutResource initialMap) { this.initialMap = initialMap; }

    public static class MapLayoutResource {
        @Min(0)
        private int totalSpaces;
        @Min(0)
        private int disabilitySpaces;
        @NotNull
        private String layoutJson;

        public MapLayoutResource() {}

        public MapLayoutResource(int totalSpaces, int disabilitySpaces, String layoutJson) {
            this.totalSpaces = totalSpaces;
            this.disabilitySpaces = disabilitySpaces;
            this.layoutJson = layoutJson;
        }

        public int getTotalSpaces() { return totalSpaces; }
        public void setTotalSpaces(int totalSpaces) { this.totalSpaces = totalSpaces; }

        public int getDisabilitySpaces() { return disabilitySpaces; }
        public void setDisabilitySpaces(int disabilitySpaces) { this.disabilitySpaces = disabilitySpaces; }

        public String getLayoutJson() { return layoutJson; }
        public void setLayoutJson(String layoutJson) { this.layoutJson = layoutJson; }
    }
}
