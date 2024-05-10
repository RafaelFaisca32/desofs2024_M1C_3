package com.mycompany.myapp.domain.driver;

import com.mycompany.myapp.domain.customer.CustomerId;
import jakarta.persistence.Embeddable;

import java.util.UUID;
@Embeddable
public class DriverId {
    private UUID id;

    public DriverId() {
        id = UUID.randomUUID();
    }

    public DriverId(UUID id) {
        this.id = id;
    }

    public UUID value(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverId driverId = (DriverId) o;
        return id.equals(driverId.id);
    }
}
