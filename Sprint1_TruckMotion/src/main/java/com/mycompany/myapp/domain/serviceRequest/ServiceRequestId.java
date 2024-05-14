package com.mycompany.myapp.domain.serviceRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class ServiceRequestId {
    @Column(name = "id")
    private UUID id;

    public ServiceRequestId() {
        id = UUID.randomUUID();
    }

    public ServiceRequestId(UUID id) {
        this.id = id;
    }

    public UUID value(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequestId serviceRequestId = (ServiceRequestId) o;
        return id.equals(serviceRequestId.id);
    }
}
