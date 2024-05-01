package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A Service.
 */
@Entity
@Table(name = "service")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "items")
    private String items;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "total_weight_of_items")
    private Float totalWeightOfItems;

    @Column(name = "price")
    private Float price;

    @Column(name = "date")
    private ZonedDateTime date;

    @JsonIgnoreProperties(value = { "customer", "service", "transport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Location location;

    @JsonIgnoreProperties(value = { "user", "locations", "service" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
    @JsonIgnoreProperties(value = { "service" }, allowSetters = true)
    private Set<ServiceStatus> serviceStatuses = new HashSet<>();

    @JsonIgnoreProperties(value = { "location", "driver", "service" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "service")
    private Transport transport;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Service id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getItems() {
        return this.items;
    }

    public Service items(String items) {
        this.setItems(items);
        return this;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public Service serviceName(String serviceName) {
        this.setServiceName(serviceName);
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Float getTotalWeightOfItems() {
        return this.totalWeightOfItems;
    }

    public Service totalWeightOfItems(Float totalWeightOfItems) {
        this.setTotalWeightOfItems(totalWeightOfItems);
        return this;
    }

    public void setTotalWeightOfItems(Float totalWeightOfItems) {
        this.totalWeightOfItems = totalWeightOfItems;
    }

    public Float getPrice() {
        return this.price;
    }

    public Service price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public Service date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Service location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Service customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Set<ServiceStatus> getServiceStatuses() {
        return this.serviceStatuses;
    }

    public void setServiceStatuses(Set<ServiceStatus> serviceStatuses) {
        if (this.serviceStatuses != null) {
            this.serviceStatuses.forEach(i -> i.setService(null));
        }
        if (serviceStatuses != null) {
            serviceStatuses.forEach(i -> i.setService(this));
        }
        this.serviceStatuses = serviceStatuses;
    }

    public Service serviceStatuses(Set<ServiceStatus> serviceStatuses) {
        this.setServiceStatuses(serviceStatuses);
        return this;
    }

    public Service addServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatuses.add(serviceStatus);
        serviceStatus.setService(this);
        return this;
    }

    public Service removeServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatuses.remove(serviceStatus);
        serviceStatus.setService(null);
        return this;
    }

    public Transport getTransport() {
        return this.transport;
    }

    public void setTransport(Transport transport) {
        if (this.transport != null) {
            this.transport.setService(null);
        }
        if (transport != null) {
            transport.setService(this);
        }
        this.transport = transport;
    }

    public Service transport(Transport transport) {
        this.setTransport(transport);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Service)) {
            return false;
        }
        return getId() != null && getId().equals(((Service) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Service{" +
            "id=" + getId() +
            ", items='" + getItems() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", totalWeightOfItems=" + getTotalWeightOfItems() +
            ", price=" + getPrice() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
