package com.mycompany.myapp.domain.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.transport.Transport;
import jakarta.persistence.*;
import java.io.Serializable;

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
    public Location(Location location){
        this.id = location.getId();
        this.coord = location.getCoord();
        this.customer = location.getCustomer();
        this.serviceRequest = location.getServiceRequest();
        this.transport = location.getTransport();

    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public LocationId getId() {
        return this.id != null ? new LocationId(this.id.value()) : null;
    }

    public GeographicalCoordinates getCoord(){
        return this.coord != null ?
            new GeographicalCoordinates(this.coord.xValue(),this.coord.yValue(),this.coord.zValue()) : null;
    }
    public void updateCoord(GeographicalCoordinates coord){
        this.coord = coord != null ?
            new GeographicalCoordinates(this.coord.xValue(),this.coord.yValue(),this.coord.zValue()) : null;
    }

    public Customer getCustomer() {
        return this.customer != null ? new Customer(this.customer) : null;
    }

    public void updateCustomer(Customer customer) {
        this.customer = customer != null ? new Customer(customer): null;
    }

    public ServiceRequest getServiceRequest() {
        return this.serviceRequest != null ? new ServiceRequest(this.serviceRequest) : null;
    }

    public void updateServiceRequest(ServiceRequest serviceRequest) {
        if (this.serviceRequest != null) {
            this.serviceRequest.updateLocation(null);
        }
        if (serviceRequest != null) {
            serviceRequest.updateLocation(this);
        }
        this.serviceRequest = serviceRequest != null ? new ServiceRequest(serviceRequest) : null;
    }

    public Transport getTransport() {
        return this.transport != null ? new Transport(this.transport) : null;
    }

    public void updateTransport(Transport transport) {
        if (this.transport != null) {
            this.transport.updateLocation(null);
        }
        if (transport != null) {
            transport.updateLocation(this);
        }
        this.transport = transport != null ? new Transport(transport) : null;
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
        String id = this.id != null ? this.id.toString() : "null";
        String coords = this.coord != null ? this.coord.toString() : "null";
        return "Location{" +
            "id=" + id +
            ", coords=" + coords +
            "}";
    }
}
