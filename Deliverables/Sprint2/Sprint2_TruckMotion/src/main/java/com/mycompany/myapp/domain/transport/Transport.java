package com.mycompany.myapp.domain.transport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Transport.
 */
@Entity
@Table(name = "transport")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Transport implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @GeneratedValue
    @Column(name = "id")
    private TransportId id;

    @Column(name = "start_time")
    @Embedded
    private TransportStartTime startTime;

    @Column(name = "end_time")
    @Embedded
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

    public Transport(Transport transport){
        this.id = transport.getId();
        this.startTime = transport.getStartTime();
        this.endTime = transport.getEndTime();
        this.location = transport.getLocation();
        this.driver = transport.getDriver();
        this.serviceRequest = transport.getServiceRequest();
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

    public TransportStartTime getStartTime() {
        return this.startTime != null ? new TransportStartTime(this.startTime.value()) : null;
    }

    public void updateStartTime(TransportStartTime startTime) {
        this.startTime = startTime != null ? new TransportStartTime(this.startTime.value()) : null;
    }

    public TransportEndTime getEndTime() {
        return this.endTime != null ? new TransportEndTime(this.endTime.value()) : null;
    }

    public void updateEndTime(TransportEndTime endTime) {
        this.endTime = endTime != null ? new TransportEndTime(this.endTime.value()) : null;
    }

    public Location getLocation() {
        return this.location != null ? new Location(this.location) : null;
    }

    public void updateLocation(Location location) {
        this.location = location != null ? new Location(location) : null;
    }

    public Driver getDriver() {
        return this.driver != null ? new Driver(this.driver) : null;
    }

    public void updateDriver(Driver driver) {
        this.driver = driver != null ? new Driver(driver) : null;
    }

    public ServiceRequest getServiceRequest() {
        return this.serviceRequest != null ? new ServiceRequest(this.serviceRequest) : null;
    }

    public void updateServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest != null ? new ServiceRequest(serviceRequest) : null;
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
