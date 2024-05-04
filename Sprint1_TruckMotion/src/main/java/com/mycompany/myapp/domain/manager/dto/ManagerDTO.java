package com.mycompany.myapp.domain.manager.dto;

import com.mycompany.myapp.domain.manager.Manager;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link Manager} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ManagerDTO implements Serializable {

    private UUID id;

    private ApplicationUserDTO applicationUser;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ApplicationUserDTO getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUserDTO applicationUser) {
        this.applicationUser = applicationUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ManagerDTO)) {
            return false;
        }

        ManagerDTO managerDTO = (ManagerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, managerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagerDTO{" +
            "id='" + getId() + "'" +
            ", applicationUser=" + getApplicationUser() +
            "}";
    }
}
