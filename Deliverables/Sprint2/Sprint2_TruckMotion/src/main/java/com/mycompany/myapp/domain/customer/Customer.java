package com.mycompany.myapp.domain.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.user.ApplicationUser;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @GeneratedValue
    @Column(name = "id")
    private CustomerId id;

    @Embedded
    @Column(name = "company")
    private Company company;

    @JsonIgnoreProperties(value = { "internalUser", "driver", "manager", "customer" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private ApplicationUser applicationUser;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer", "serviceRequest", "transport" }, allowSetters = true)
    private Set<Location> locations = new HashSet<>();

    @JsonIgnoreProperties(value = { "location", "customer", "serviceStatuses", "transport" }, allowSetters = true)
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<ServiceRequest> serviceRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Customer(){
        this.id = new CustomerId();
    }

    public Customer(CustomerId id, Company company, ApplicationUser applicationUser) {
        this.id = id;
        this.company = company;
        this.applicationUser = applicationUser;
    }

    public Customer(Customer customer){
        this.id = customer.getId();
        this.company = customer.getCompany();
        this.applicationUser = customer.getApplicationUser();
        this.locations = new HashSet<>(customer.getLocations());
        this.serviceRequests = new HashSet<>(customer.getServiceRequests());

    }

    public CustomerId getId() {
        return this.id != null ? new CustomerId(this.id.value()) : null;
    }

    public Company getCompany() {
        return this.company != null ? new Company(this.company.value()) : null;
    }


    public void updateCompany(Company company) {
        this.company = company != null ? new Company(company.value()) : null;
    }

    public ApplicationUser getApplicationUser() {
        return this.applicationUser;
    }

    public void updateApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser != null ? new ApplicationUser(applicationUser) : null;
    }


    public Set<Location> getLocations() {
        return this.locations != null ? new HashSet<>(this.locations) : new HashSet<>();
    }

    public void updateLocations(Set<Location> locations) {
        if(locations != null) {
            locations.forEach(i -> i.updateCustomer(this));
        }

        if (this.locations != null) {
            this.locations.forEach(i -> i.updateCustomer(null));
        }
        this.locations = locations != null ? new HashSet<>(locations) : new HashSet<>();
    }

    public void addLocation(Location location) {
        if(location != null) {
            location.updateCustomer(this);
            Location newLocation = new Location(location);
            this.locations.add(newLocation);
        }
    }

    public void removeLocation(Location location) {
        if(location != null) {
            location.updateCustomer(null);
            this.locations.remove(location);
        }
    }

    public Set<ServiceRequest> getServiceRequests() {
        return this.serviceRequests != null ? new HashSet<>(this.serviceRequests) : new HashSet<>();
    }

    public void updateServiceRequest(Set<ServiceRequest> serviceRequests) {

        if(serviceRequests != null) {
            serviceRequests.forEach(i -> i.updateCustomer(this));
        }

        if (this.serviceRequests != null) {
            this.serviceRequests.forEach(i -> i.updateCustomer(null));
        }

        this.serviceRequests = serviceRequests != null ? new HashSet<>(serviceRequests) : new HashSet<>();
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

        String id = this.id != null ? this.id.toString() : "null";
        String company = this.company != null ? this.company.toString() : "null";

        return "Customer{" +
            "id=" + id +
            ", company='" + company + "'" +
            "}";
    }
}
