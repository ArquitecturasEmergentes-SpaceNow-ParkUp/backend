package pe.edu.upc.ParkUp.ParkUp_platform.affiliate.domain.model.valueobjects;

import java.util.Objects;

/**
 * Value object for Map identifier.
 */
public final class MapId {
    private final Long value;

    public MapId(Long value) {
        this.value = Objects.requireNonNull(value);
    }

    public Long getValue() { return value; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapId)) return false;
        return value.equals(((MapId)o).value);
    }

    @Override public int hashCode() { return value.hashCode(); }

    @Override public String toString() { return String.valueOf(value); }
}
