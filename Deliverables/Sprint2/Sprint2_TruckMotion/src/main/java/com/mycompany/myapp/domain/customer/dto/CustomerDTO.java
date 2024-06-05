package com.mycompany.myapp.domain.customer.dto;

import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A DTO for the {@link Customer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerDTO implements Serializable {

    private UUID id;

    private String company;

    private ApplicationUserDTO applicationUser;

    private Set<LocationDTO> locations = new HashSet<>();
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public ApplicationUserDTO getApplicationUser() {
        return applicationUser;
    }

    public Set<LocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(Set<LocationDTO> locations) {
        this.locations = locations;
    }

    public void setApplicationUser(ApplicationUserDTO applicationUser) {
        this.applicationUser = applicationUser;
    }

    public CustomerDTO(UUID id, String company, ApplicationUserDTO applicationUser, Set<LocationDTO> locations) {
        this.id = id;
        this.company = company;
        this.applicationUser = applicationUser;
        this.locations = locations;
    }

    public CustomerDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDTO)) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id='" + getId() + "'" +
            ", company='" + getCompany() + "'" +
            ", applicationUser=" + getApplicationUser() +
            "}";
    }
}
