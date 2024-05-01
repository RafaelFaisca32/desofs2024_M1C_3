package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Manager} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ManagerDTO implements Serializable {

    private UUID id;

    private UserDTO user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
            ", user=" + getUser() +
            "}";
    }
}
