package com.mycompany.myapp.domain.transport;

import com.mycompany.myapp.domain.driver.DriverId;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class TransportId {
    private UUID id;

    public TransportId() {
        this.id = UUID.randomUUID();
    }

    public TransportId(UUID id) {
        this.id = id;
    }

    public UUID value(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportId transportId = (TransportId) o;
        return id.equals(transportId.id);
    }

}
