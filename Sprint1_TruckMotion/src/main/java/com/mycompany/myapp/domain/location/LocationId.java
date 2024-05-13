package com.mycompany.myapp.domain.location;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class LocationId {

    private UUID id;

    public LocationId(UUID id) {
        this.id = id;
    }

    public LocationId() {
        this.id = UUID.randomUUID();
    }

    public UUID value(){
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationId that = (LocationId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
