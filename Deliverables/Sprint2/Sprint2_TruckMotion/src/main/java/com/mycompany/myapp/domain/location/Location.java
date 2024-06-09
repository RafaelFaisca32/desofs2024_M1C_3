package com.mycompany.myapp.domain.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.transport.Transport;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private Set<ServiceRequest> serviceRequests = new HashSet<>();

    @JsonIgnoreProperties(value = { "location", "driver", "serviceRequest" }, allowSetters = true)
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private Set<Transport> transports = new HashSet<>();

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
        this.serviceRequests = location.getServiceRequests();
        this.transports = location.getTransports();

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
        return this.customer;
    }

    public void updateCustomer(Customer customer) {
        this.customer = customer != null ? new Customer(customer): null;
    }

    public Set<ServiceRequest> getServiceRequests() {
        return this.serviceRequests != null ? new HashSet<>(this.serviceRequests) : new HashSet<>();
    }

    public void updateServiceRequest(Set<ServiceRequest> serviceRequests) {
        if(serviceRequests != null) {
            serviceRequests.forEach(i -> i.updateLocation(this));
        }

        if (this.serviceRequests != null) {
            this.serviceRequests.forEach(i -> i.updateLocation(null));
        }
        this.serviceRequests = serviceRequests != null ? new HashSet<>(serviceRequests) : new HashSet<>();
    }

    public Set<Transport> getTransports() {
        return this.transports != null ? new HashSet<>(this.transports) : new HashSet<>();
    }

    public void updateTransport(Set<Transport> transports) {
        if(transports != null) {
            transports.forEach(i -> i.updateLocation(this));
        }

        if (this.transports != null) {
            this.transports.forEach(i -> i.updateLocation(null));
        }
        this.transports = transports != null ? new HashSet<>(transports) : null;
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
