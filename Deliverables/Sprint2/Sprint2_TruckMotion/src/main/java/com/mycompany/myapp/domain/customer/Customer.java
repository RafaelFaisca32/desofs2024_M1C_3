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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
    private ServiceRequest serviceRequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Customer(){
        this.id = new CustomerId();
    }

    public Customer(CustomerId id, Company company, ApplicationUser applicationUser) {
        this.id = id;
        this.company = company;
        this.applicationUser = applicationUser;
    }

    public CustomerId getId() {
        return this.id != null ? new CustomerId(this.id.value()) : null;
    }

    public Company getCompany() {
        return this.company != null ? new Company(this.company.value()) : null;
    }

    public Customer updateCompanyWithReturn(Company company) {
        this.updateCompany(new Company(company.value()));
        return this;
    }

    public void updateCompany(Company company) {
        this.company = new Company(company.value());
    }

    public ApplicationUser getApplicationUser() {
        //return this.applicationUser.mirror();
        return null;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Customer applicationUser(ApplicationUser applicationUser) {
        this.setApplicationUser(applicationUser);
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

        String id = this.id != null ? this.id.toString() : "null";
        String company = this.company != null ? this.company.toString() : "null";

        return "Customer{" +
            "id=" + id +
            ", company='" + company + "'" +
            "}";
    }
}
