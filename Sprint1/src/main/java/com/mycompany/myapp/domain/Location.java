package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "coord_x")
    private Float coordX;

    @Column(name = "coord_y")
    private Float coordY;

    @Column(name = "coord_z")
    private Float coordZ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "locations", "service" }, allowSetters = true)
    private Customer customer;

    @JsonIgnoreProperties(value = { "location", "customer", "serviceStatuses", "transport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "location")
    private Service service;

    @JsonIgnoreProperties(value = { "location", "driver", "service" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "location")
    private Transport transport;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Location id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Float getCoordX() {
        return this.coordX;
    }

    public Location coordX(Float coordX) {
        this.setCoordX(coordX);
        return this;
    }

    public void setCoordX(Float coordX) {
        this.coordX = coordX;
    }

    public Float getCoordY() {
        return this.coordY;
    }

    public Location coordY(Float coordY) {
        this.setCoordY(coordY);
        return this;
    }

    public void setCoordY(Float coordY) {
        this.coordY = coordY;
    }

    public Float getCoordZ() {
        return this.coordZ;
    }

    public Location coordZ(Float coordZ) {
        this.setCoordZ(coordZ);
        return this;
    }

    public void setCoordZ(Float coordZ) {
        this.coordZ = coordZ;
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

    public Service getService() {
        return this.service;
    }

    public void setService(Service service) {
        if (this.service != null) {
            this.service.setLocation(null);
        }
        if (service != null) {
            service.setLocation(this);
        }
        this.service = service;
    }

    public Location service(Service service) {
        this.setService(service);
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
            ", coordX=" + getCoordX() +
            ", coordY=" + getCoordY() +
            ", coordZ=" + getCoordZ() +
            "}";
    }
}
