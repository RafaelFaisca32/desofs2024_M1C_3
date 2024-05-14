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

    @Id
    @GeneratedValue
    @Column(name = "id")
    private TransportId id;

    @Column(name = "make")
    private Make make;

    @Column(name = "model")
    private Model model;

    @JsonIgnoreProperties(value = { "truck", "applicationUser", "transport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "truck")
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public TransportId getId() {
        return new TransportId(this.id.value());
    }

    public Truck id(TransportId id) {
        this.setId(id);
        return this;
    }

    public void setId(TransportId id) {
        this.id = id;
    }

    public Make getMake() {
        return this.make;
    }

    public Truck make(Make make) {
        this.setMake(make);
        return this;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public Model getModel() {
        return this.model;
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
        return "Truck{" +
            "id=" + getId() +
            ", make='" + getMake() + "'" +
            ", model='" + getModel() + "'" +
            "}";
    }
}
