package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects;


import java.util.Objects;


public final class ParkingLotId {
    private final Long value;

    public ParkingLotId(Long value) {
        this.value = Objects.requireNonNull(value);
    }

    public Long getValue() {
        return value;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingLotId)) return false;
        ParkingLotId that = (ParkingLotId) o;
        return value.equals(that.value);
    }

    @Override public int hashCode() { return value.hashCode(); }

    @Override public String toString() { return String.valueOf(value); }
}
