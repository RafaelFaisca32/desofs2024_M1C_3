package com.mycompany.myapp.domain.serviceRequest.dto;

import com.mycompany.myapp.domain.serviceRequest.Status;
import com.mycompany.myapp.domain.serviceRequest.ServiceStatus;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ServiceStatus} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceStatusDTO implements Serializable {

    private UUID id;

    private ZonedDateTime date;

    private String observations;

    private Status status;

    private ServiceRequestDTO serviceRequest;

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

    public ServiceRequestDTO getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequestDTO serviceRequest) {
        this.serviceRequest = serviceRequest;
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
            ", serviceRequest=" + getServiceRequest() +
            "}";
    }
}
