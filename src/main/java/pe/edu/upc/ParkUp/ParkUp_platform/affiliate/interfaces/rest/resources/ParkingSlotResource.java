package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.interfaces.rest.resources;

public class ParkingSlotResource {

    private Long id;
    private String code;
    private boolean disability;
    private String status;
    private Long parkingLotId;

    public ParkingSlotResource() {}

    public ParkingSlotResource(Long id, String code, boolean disability, String status, Long parkingLotId) {
        this.id = id;
        this.code = code;
        this.disability = disability;
        this.status = status;
        this.parkingLotId = parkingLotId;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public boolean isDisability() { return disability; }
    public String getStatus() { return status; }
    public Long getParkingLotId() { return parkingLotId; }

    public void setId(Long id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setDisability(boolean disability) { this.disability = disability; }
    public void setStatus(String status) { this.status = status; }
    public void setParkingLotId(Long parkingLotId) { this.parkingLotId = parkingLotId; }
}
