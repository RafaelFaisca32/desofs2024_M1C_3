package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ServiceStatus} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceStatusDTO implements Serializable {

    private UUID id;

    private ZonedDateTime date;

    private String observations;

    private Status status;

    private ServiceDTO service;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ServiceDTO getService() {
        return service;
    }

    public void setService(ServiceDTO service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceStatusDTO)) {
            return false;
        }

        ServiceStatusDTO serviceStatusDTO = (ServiceStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceStatusDTO{" +
            "id='" + getId() + "'" +
            ", date='" + getDate() + "'" +
            ", observations='" + getObservations() + "'" +
            ", status='" + getStatus() + "'" +
            ", service=" + getService() +
            "}";
    }
}
