package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Truck} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TruckDTO implements Serializable {

    private UUID id;

    private String make;

    private String model;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TruckDTO)) {
            return false;
        }

        TruckDTO truckDTO = (TruckDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, truckDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TruckDTO{" +
            "id='" + getId() + "'" +
            ", make='" + getMake() + "'" +
            ", model='" + getModel() + "'" +
            "}";
    }
}
