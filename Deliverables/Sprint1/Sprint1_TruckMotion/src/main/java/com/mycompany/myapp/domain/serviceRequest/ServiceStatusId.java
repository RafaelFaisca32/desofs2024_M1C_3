package com.mycompany.myapp.domain.serviceRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class ServiceStatusId {
    @Column(name = "id")
    private UUID id;

    public ServiceStatusId() {
        id = UUID.randomUUID();
    }

    public ServiceStatusId(UUID id) {
        this.id = id;
    }

    public UUID value(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceStatusId serviceStatusId = (ServiceStatusId) o;
        return id.equals(serviceStatusId.id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
