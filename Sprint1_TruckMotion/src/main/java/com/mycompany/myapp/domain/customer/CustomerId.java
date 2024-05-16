package com.mycompany.myapp.domain.customer;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CustomerId {
    private UUID id;

    public CustomerId() {
        id = UUID.randomUUID();
    }

    public CustomerId(UUID id) {
        this.id = id;
    }

    public UUID value(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerId customerId = (CustomerId) o;
        return id.equals(customerId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
