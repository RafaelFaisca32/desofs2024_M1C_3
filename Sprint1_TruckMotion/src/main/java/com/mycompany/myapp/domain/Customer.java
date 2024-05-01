package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "company")
    private String company;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer", "serviceRequest", "transport" }, allowSetters = true)
    private Set<Location> locations = new HashSet<>();

    @JsonIgnoreProperties(value = { "location", "customer", "serviceStatuses", "transport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
    private ServiceRequest serviceRequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Customer id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompany() {
        return this.company;
    }

    public Customer company(String company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(Set<Location> locations) {
        if (this.locations != null) {
            this.locations.forEach(i -> i.setCustomer(null));
        }
        if (locations != null) {
            locations.forEach(i -> i.setCustomer(this));
        }
        this.locations = locations;
    }

    public Customer locations(Set<Location> locations) {
        this.setLocations(locations);
        return this;
    }

    public Customer addLocation(Location location) {
        this.locations.add(location);
        location.setCustomer(this);
        return this;
    }

    public Customer removeLocation(Location location) {
        this.locations.remove(location);
        location.setCustomer(null);
        return this;
    }

    public ServiceRequest getServiceRequest() {
        return this.serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        if (this.serviceRequest != null) {
            this.serviceRequest.setCustomer(null);
        }
        if (serviceRequest != null) {
            serviceRequest.setCustomer(this);
        }
        this.serviceRequest = serviceRequest;
    }

    public Customer serviceRequest(ServiceRequest serviceRequest) {
        this.setServiceRequest(serviceRequest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return getId() != null && getId().equals(((Customer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            "}";
    }
}
