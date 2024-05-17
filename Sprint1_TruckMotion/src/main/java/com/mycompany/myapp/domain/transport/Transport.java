package com.mycompany.myapp.domain.transport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A Transport.
 */
@Entity
@Table(name = "transport")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Transport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private TransportId id;

    @Column(name = "start_time")
    private TransportStartTime startTime;

    @Column(name = "end_time")
    private TransportEndTime endTime;

    @JsonIgnoreProperties(value = { "customer", "serviceRequest", "transport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true)
    private Location location;

    @JsonIgnoreProperties(value = { "truck", "applicationUser", "transport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true)
    private Driver driver;

    @JsonIgnoreProperties(value = { "location", "customer", "serviceStatuses", "transport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true)
    private ServiceRequest serviceRequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Transport(){

    }

    public Transport(TransportId id, TransportStartTime startTime, TransportEndTime endTime, Location location, Driver driver, ServiceRequest serviceRequest) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.driver = driver;
        this.serviceRequest = serviceRequest;
    }

    public TransportId getId() {
        return this.id != null ? new TransportId(id.value()) : null;
    }

    public Transport id(TransportId id) {
        this.setId(id);
        return this;
    }

    public void setId(TransportId id) {
        this.id = id;
    }

    public TransportStartTime getStartTime() {
        return this.startTime != null ? new TransportStartTime(this.startTime.value()) : null;
    }

    public Transport startTime(TransportStartTime startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(TransportStartTime startTime) {
        this.startTime = startTime;
    }

    public TransportEndTime getEndTime() {
        return this.endTime != null ? new TransportEndTime(this.endTime.value()) : null;
    }

    public Transport endTime(TransportEndTime endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(TransportEndTime endTime) {
        this.endTime = endTime;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Transport location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Transport driver(Driver driver) {
        this.setDriver(driver);
        return this;
    }

    public ServiceRequest getServiceRequest() {
        return this.serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public Transport serviceRequest(ServiceRequest serviceRequest) {
        this.setServiceRequest(serviceRequest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transport)) {
            return false;
        }
        return getId() != null && getId().equals(((Transport) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        String id = this.id != null ? this.id.toString() : "null";
        String startTime = this.startTime != null ? this.startTime.toString() : "null";
        String endTime = this.endTime != null ? this.endTime.toString() : "null";

        return "Transport{" +
            "id=" + id +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            "}";
    }
}
