package com.mycompany.myapp.domain.user.dto;

import com.mycompany.myapp.domain.user.Gender;
import com.mycompany.myapp.domain.user.ApplicationUser;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ApplicationUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUserDTO implements Serializable {

    private Long id;
    private UUID uuidId;

    private LocalDate birthDate;

    private Gender gender;

    private UserDTO internalUser;

    public Long getId() {
        return id;
    }
    public UUID getUuidId() {
        return uuidId;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setUuidId(UUID uuidId) {
        this.uuidId = uuidId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UserDTO getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(UserDTO internalUser) {
        this.internalUser = internalUser;
    }

    public ApplicationUserDTO(){}
    public ApplicationUserDTO(Long id, UUID uuidId, LocalDate birthDate, Gender gender, UserDTO internalUser) {
        this.id = id;
        this.uuidId = uuidId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.internalUser = internalUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUserDTO)) {
            return false;
        }

        ApplicationUserDTO applicationUserDTO = (ApplicationUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicationUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUserDTO{" +
            "id=" + getId() +
            ", birthDate='" + getBirthDate() + "'" +
            ", gender='" + getGender() + "'" +
            ", internalUser=" + getInternalUser() +
            "}";
    }
}
