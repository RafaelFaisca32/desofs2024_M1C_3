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
    @EmbeddedId
    @GeneratedValue
    @Column(name = "id")
    private ServiceRequestId id;

    @Column(name = "items")
    private ServiceRequestItems items;

    @Column(name = "service_name")
    private ServiceRequestName serviceName;

    @Column(name = "total_weight_of_items")
    private ServiceRequestTotalWeightOfItems totalWeightOfItems;

    @Column(name = "price")
    private ServiceRequestPrice price;

    @Column(name = "date")
    private ServiceRequestDate date;

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


    public ServiceRequest(ServiceRequestId id, ServiceRequestItems items, ServiceRequestName serviceName, ServiceRequestTotalWeightOfItems totalWeightOfItems, ServiceRequestPrice price, ServiceRequestDate date) {
        this.id = id;
        this.items = items;
        this.serviceName = serviceName;
        this.totalWeightOfItems = totalWeightOfItems;
        this.price = price;
        this.date = date;
    }

    public ServiceRequest() {

    }

    public ServiceRequestId getId() {
        return new ServiceRequestId(id.value());
    }

    public ServiceRequest id(ServiceRequestId id) {
        this.setId(id);
        return this;
    }

    public void setId(ServiceRequestId id) {
        this.id = id;
    }

    public ServiceRequestItems getItems() {
        return new ServiceRequestItems(this.items.value());
    }

    public ServiceRequest items(ServiceRequestItems items) {
        this.setItems(items);
        return this;
    }

    public void setItems(ServiceRequestItems items) {
        this.items = items;
    }

    public ServiceRequestName getServiceName() {
        return new ServiceRequestName(this.serviceName.value());
    }

    public ServiceRequest serviceName(ServiceRequestName serviceName) {
        this.setServiceName(serviceName);
        return this;
    }

    public void setServiceName(ServiceRequestName serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceRequestTotalWeightOfItems getTotalWeightOfItems() {
        return new ServiceRequestTotalWeightOfItems(totalWeightOfItems.value());
    }

    public ServiceRequest totalWeightOfItems(ServiceRequestTotalWeightOfItems totalWeightOfItems) {
        this.setTotalWeightOfItems(totalWeightOfItems);
        return this;
    }

    public void setTotalWeightOfItems(ServiceRequestTotalWeightOfItems totalWeightOfItems) {
        this.totalWeightOfItems = totalWeightOfItems;
    }

    public ServiceRequestPrice getPrice() {
        return new ServiceRequestPrice(price.value());
    }

    public ServiceRequest price(ServiceRequestPrice price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(ServiceRequestPrice price) {
        this.price = price;
    }

    public ServiceRequestDate getDate() {
        return new ServiceRequestDate(date.value());
    }

    public ServiceRequest date(ServiceRequestDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ServiceRequestDate date) {
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
