package com.mycompany.myapp.domain.driver.dto;

import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.truck.dto.TruckDTO;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link Driver} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DriverDTO implements Serializable {

    private UUID id;

    private TruckDTO truck;

    private ApplicationUserDTO applicationUser;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TruckDTO getTruck() {
        return truck;
    }

    public void setTruck(TruckDTO truck) {
        this.truck = truck;
    }

    public ApplicationUserDTO getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUserDTO applicationUser) {
        this.applicationUser = applicationUser;
    }

    public DriverDTO(){}

    public DriverDTO(UUID id, TruckDTO truck, ApplicationUserDTO applicationUser) {
        this.id = id;
        this.truck = truck;
        this.applicationUser = applicationUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DriverDTO)) {
            return false;
        }

        DriverDTO driverDTO = (DriverDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, driverDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DriverDTO{" +
            "id='" + getId() + "'" +
            ", truck=" + getTruck() +
            ", applicationUser=" + getApplicationUser() +
            "}";
    }
}
