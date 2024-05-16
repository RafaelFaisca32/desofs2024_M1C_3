package com.mycompany.myapp.domain.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.transport.Transport;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @GeneratedValue
    @Column(name = "id")
    private LocationId id;

    @Embedded
    private GeographicalCoordinates coord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "applicationUser", "locations", "serviceRequest" }, allowSetters = true)
    private Customer customer;

    @JsonIgnoreProperties(value = { "location", "customer", "serviceStatuses", "transport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "location")
    private ServiceRequest serviceRequest;

    @JsonIgnoreProperties(value = { "location", "driver", "serviceRequest" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "location")
    private Transport transport;

    public Location(LocationId id, GeographicalCoordinates coord, Customer customer) {
        this.id = id;
        this.coord = coord;
        this.customer = customer;
    }

    public Location() {
        this.id = new LocationId();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public LocationId getId() {
        return new LocationId(this.id.value());
    }

    public Location id(LocationId id) {
        this.setId(id);
        return this;
    }


    public void setId(LocationId id) {
        this.id = id;
    }

    public GeographicalCoordinates getCoord(){
        return this.coord;
    }
    public void setCoord(GeographicalCoordinates coord){
        this.coord = coord;
    }

    public Location coord(GeographicalCoordinates coord){
        this.setCoord(coord);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Location customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public ServiceRequest getServiceRequest() {
        return this.serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        if (this.serviceRequest != null) {
            this.serviceRequest.setLocation(null);
        }
        if (serviceRequest != null) {
            serviceRequest.setLocation(this);
        }
        this.serviceRequest = serviceRequest;
    }

    public Location serviceRequest(ServiceRequest serviceRequest) {
        this.setServiceRequest(serviceRequest);
        return this;
    }

    public Transport getTransport() {
        return this.transport;
    }

    public void setTransport(Transport transport) {
        if (this.transport != null) {
            this.transport.setLocation(null);
        }
        if (transport != null) {
            transport.setLocation(this);
        }
        this.transport = transport;
    }

    public Location transport(Transport transport) {
        this.setTransport(transport);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return getId() != null && getId().equals(((Location) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", coords=" + getCoord() +
            "}";
    }
}
