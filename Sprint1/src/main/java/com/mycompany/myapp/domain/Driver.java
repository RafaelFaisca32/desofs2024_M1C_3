package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @JsonIgnoreProperties(value = { "driver" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Truck truck;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @JsonIgnoreProperties(value = { "location", "driver", "service" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "driver")
    private Transport transport;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Driver id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Truck getTruck() {
        return this.truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Driver truck(Truck truck) {
        this.setTruck(truck);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Driver user(User user) {
        this.setUser(user);
        return this;
    }

    public Transport getTransport() {
        return this.transport;
    }

    public void setTransport(Transport transport) {
        if (this.transport != null) {
            this.transport.setDriver(null);
        }
        if (transport != null) {
            transport.setDriver(this);
        }
        this.transport = transport;
    }

    public Driver transport(Transport transport) {
        this.setTransport(transport);
        return this;
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
        return "Driver{" +
            "id=" + getId() +
            "}";
    }
}
