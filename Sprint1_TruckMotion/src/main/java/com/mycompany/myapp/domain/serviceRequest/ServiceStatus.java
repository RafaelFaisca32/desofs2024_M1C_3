package com.mycompany.myapp.domain.serviceRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A ServiceStatus.
 */
@Entity
@Table(name = "service_status")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @EmbeddedId
    @GeneratedValue
    @Column(name = "id")
    private ServiceStatusId id;

    @Column(name = "date")
    private ServiceRequestDate date;

    @Column(name = "observations")
    private ServiceStatusObservations observations;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "location", "customer", "serviceStatuses", "transport" }, allowSetters = true)
    private ServiceRequest serviceRequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public ServiceStatus(){}

    public ServiceStatus(ServiceStatusId id, ServiceRequestDate date, ServiceStatusObservations observations, Status status, ServiceRequest serviceRequest) {
        this.id = id;
        this.date = date;
        this.observations = observations;
        this.status = status;
        this.serviceRequest = serviceRequest;
    }

    public ServiceStatusId getId() {
        return new ServiceStatusId(this.id.value());
    }

    public ServiceStatus id(ServiceStatusId id) {
        this.setId(id);
        return this;
    }

    public void setId(ServiceStatusId id) {
        this.id = id;
    }

    public ServiceRequestDate getDate() {
        return new ServiceRequestDate(this.date.value());
    }

    public ServiceStatus date(ServiceRequestDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ServiceRequestDate date) {
        this.date = date;
    }

    public ServiceStatusObservations getObservations() {
        return new ServiceStatusObservations(this.observations.value());
    }

    public ServiceStatus observations(ServiceStatusObservations observations) {
        this.setObservations(observations);
        return this;
    }

    public void setObservations(ServiceStatusObservations observations) {
        this.observations = observations;
    }

    public Status getStatus() {
        return this.status;
    }

    public ServiceStatus status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ServiceRequest getServiceRequest() {
        return this.serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public ServiceStatus serviceRequest(ServiceRequest serviceRequest) {
        this.setServiceRequest(serviceRequest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceStatus)) {
            return false;
        }
        return getId() != null && getId().equals(((ServiceStatus) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceStatus{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", observations='" + getObservations() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
