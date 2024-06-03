package com.mycompany.myapp.domain.user;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ApplicationUserId {
    private UUID uuidId;

    public ApplicationUserId() {
        uuidId = UUID.randomUUID();
    }

    public ApplicationUserId(UUID uuidId) {
        this.uuidId = uuidId;
    }

    public UUID value(){
        return uuidId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationUserId applicationUserId = (ApplicationUserId) o;
        return uuidId.equals(applicationUserId.uuidId);
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
