package com.mycompany.myapp.domain.truck;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class TruckId {

    private UUID id;

    public TruckId() {
        this.id = UUID.randomUUID();
    }

    public TruckId(UUID id) {
        this.id = id;
    }

    public UUID value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckId truckId = (TruckId) o;
        return Objects.equals(id, truckId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
