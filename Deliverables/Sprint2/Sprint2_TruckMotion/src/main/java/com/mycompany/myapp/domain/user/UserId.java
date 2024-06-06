package com.mycompany.myapp.domain.user;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UserId {

    private UUID uuidId;

    public UserId() {
        this.uuidId = UUID.randomUUID();
    }

    public UserId(UUID id) {
        this.uuidId = id;
    }

    public UUID value() {
        return uuidId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(uuidId, userId.uuidId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuidId);
    }

    @Override
    public String toString() {
        return uuidId.toString();
    }
}
