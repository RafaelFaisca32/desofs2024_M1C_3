package com.mycompany.myapp.domain.driver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.transport.Transport;
import com.mycompany.myapp.domain.truck.Truck;
import com.mycompany.myapp.domain.user.ApplicationUser;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @GeneratedValue
    @Column(name = "id")
    private DriverId id;

    @JsonIgnoreProperties(value = { "driver" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Truck truck;

    @JsonIgnoreProperties(value = { "internalUser", "driver", "manager", "customer" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private ApplicationUser applicationUser;

    @JsonIgnoreProperties(value = { "location", "driver", "serviceRequest" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "driver")
    private Transport transport;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public DriverId getId() {
        return this.id != null ? new DriverId(this.id.value()) : null;
    }


    public Driver(){
        this.id = new DriverId();
    }

    public Driver(DriverId id, Truck truck, ApplicationUser applicationUser) {
        this.id = id;
        this.truck = truck;
        this.applicationUser = applicationUser;
    }

    public Driver(Driver driver){
        this.id = driver.getId();
        this.truck = driver.getTruck();
        this.applicationUser = driver.getApplicationUser();
        this.transport = driver.getTransport();
    }


    public Truck getTruck() {
        return this.truck != null ? new Truck(this.truck) : null;
    }

    public void updateTruck(Truck truck) {
        this.truck = truck != null ? new Truck(truck) : null;
    }


    public ApplicationUser getApplicationUser() {
        return this.applicationUser != null ? new ApplicationUser(this.applicationUser) : null;
    }

    public void updateApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser != null ? new ApplicationUser(applicationUser) : null;
    }

    public Transport getTransport() {
        return this.transport != null ? new Transport(this.transport) : null;
    }

    public void updateTransport(Transport transport) {


        if (this.transport != null) {
            this.transport.updateDriver(null);
        }
        if (transport != null) {
            transport.updateDriver(this);
        }
        this.transport = transport != null ? new Transport(transport) : null;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Driver)) {
            return false;
        }
        return getId() != null && getId().equals(((Driver) o).getId());
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

        return "Driver{" +
            "id=" + id +
            "}";
    }
}
