package com.mycompany.myapp.domain.manager;

import com.mycompany.myapp.domain.location.LocationId;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;
@Embeddable
public class ManagerId {
    private UUID id;

    public ManagerId(UUID id) {
        this.id = id;
    }

    public ManagerId() {
        this.id = UUID.randomUUID();
    }

    public UUID value(){
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerId that = (ManagerId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
