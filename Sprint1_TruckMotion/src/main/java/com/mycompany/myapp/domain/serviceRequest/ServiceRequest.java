package com.mycompany.myapp.domain.serviceRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.transport.Transport;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A ServiceRequest.
 */
@Entity
@Table(name = "service_request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceRequest implements Serializable {

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

    @JsonIgnoreProperties(value = { "customer", "serviceRequest", "transport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Location location;

    @JsonIgnoreProperties(value = { "applicationUser", "locations", "serviceRequest" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceRequest")
    @JsonIgnoreProperties(value = { "serviceRequest" }, allowSetters = true)
    private Set<ServiceStatus> serviceStatuses = new HashSet<>();

    @JsonIgnoreProperties(value = { "location", "driver", "serviceRequest" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "serviceRequest")
    private Transport transport;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ServiceRequest id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getItems() {
        return this.items;
    }

    public ServiceRequest items(String items) {
        this.setItems(items);
        return this;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public ServiceRequest serviceName(String serviceName) {
        this.setServiceName(serviceName);
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Float getTotalWeightOfItems() {
        return this.totalWeightOfItems;
    }

    public ServiceRequest totalWeightOfItems(Float totalWeightOfItems) {
        this.setTotalWeightOfItems(totalWeightOfItems);
        return this;
    }

    public void setTotalWeightOfItems(Float totalWeightOfItems) {
        this.totalWeightOfItems = totalWeightOfItems;
    }

    public Float getPrice() {
        return this.price;
    }

    public ServiceRequest price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public ServiceRequest date(ZonedDateTime date) {
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

    public ServiceRequest location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ServiceRequest customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Set<ServiceStatus> getServiceStatuses() {
        return this.serviceStatuses;
    }

    public void setServiceStatuses(Set<ServiceStatus> serviceStatuses) {
        if (this.serviceStatuses != null) {
            this.serviceStatuses.forEach(i -> i.setServiceRequest(null));
        }
        if (serviceStatuses != null) {
            serviceStatuses.forEach(i -> i.setServiceRequest(this));
        }
        this.serviceStatuses = serviceStatuses;
    }

    public ServiceRequest serviceStatuses(Set<ServiceStatus> serviceStatuses) {
        this.setServiceStatuses(serviceStatuses);
        return this;
    }

    public ServiceRequest addServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatuses.add(serviceStatus);
        serviceStatus.setServiceRequest(this);
        return this;
    }

    public ServiceRequest removeServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatuses.remove(serviceStatus);
        serviceStatus.setServiceRequest(null);
        return this;
    }

    public Transport getTransport() {
        return this.transport;
    }

    public void setTransport(Transport transport) {
        if (this.transport != null) {
            this.transport.setServiceRequest(null);
        }
        if (transport != null) {
            transport.setServiceRequest(this);
        }
        this.transport = transport;
    }

    public ServiceRequest transport(Transport transport) {
        this.setTransport(transport);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceRequest)) {
            return false;
        }
        return getId() != null && getId().equals(((ServiceRequest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceRequest{" +
            "id=" + getId() +
            ", items='" + getItems() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", totalWeightOfItems=" + getTotalWeightOfItems() +
            ", price=" + getPrice() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
