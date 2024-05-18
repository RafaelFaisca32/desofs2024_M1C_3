package com.mycompany.myapp.domain.truck;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.transport.TransportId;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A Truck.
 */
@Entity
@Table(name = "truck")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Truck implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @GeneratedValue
    @Column(name = "id")
    private TruckId id;

    @Embedded
    @Column(name = "make")
    private Make make;

    @Embedded
    @Column(name = "model")
    private Model model;

    @JsonIgnoreProperties(value = { "truck", "applicationUser", "transport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "truck")
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Truck() {
        this.id = new TruckId();
    }

    public Truck(TruckId id,Make make,Model model) {
        this.id = id;
        this.model = model;
        this.make = make;

    }

    public TruckId getId() {
        return this.id != null ? new TruckId(this.id.value()) : null;
    }

    public Truck id(TruckId id) {
        this.setId(id);
        return this;
    }

    public void setId(TruckId id) {
        this.id = id;
    }

    public Make getMake() {
        return this.make != null ? new Make(this.make.value()) : null;
    }

    public Truck make(Make make) {
        this.setMake(make);
        return this;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public Model getModel() {
        return this.model != null ? new Model(this.model.value()) : null;
    }

    public Truck model(Model model) {
        this.setModel(model);
        return this;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        if (this.driver != null) {
            this.driver.setTruck(null);
        }
        if (driver != null) {
            driver.setTruck(this);
        }
        this.driver = driver;
    }

    public Truck driver(Driver driver) {
        this.setDriver(driver);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Truck)) {
            return false;
        }
        return getId() != null && getId().equals(((Truck) o).getId());
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
        String make = this.make != null ? this.make.toString() : "null";
        String model = this.model != null ? this.model.toString() : "null";
        return "Truck{" +
            "id=" + id +
            ", make='" + make + "'" +
            ", model='" + model + "'" +
            "}";
    }
}
