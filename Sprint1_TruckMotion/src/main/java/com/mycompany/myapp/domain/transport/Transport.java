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
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

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

    public Transport(TransportId id, ZonedDateTime startTime, ZonedDateTime endTime, Location location, Driver driver, ServiceRequest serviceRequest) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.driver = driver;
        this.serviceRequest = serviceRequest;
    }

    public TransportId getId() {
        return this.id;
    }

    public Transport id(TransportId id) {
        this.setId(id);
        return this;
    }

    public void setId(TransportId id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return this.startTime;
    }

    public Transport startTime(ZonedDateTime startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return this.endTime;
    }

    public Transport endTime(ZonedDateTime endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
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
        return "Transport{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
