package com.mycompany.myapp.domain.serviceRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceStatusDTO;
import com.mycompany.myapp.domain.serviceRequest.mapper.ServiceStatusMapper;
import com.mycompany.myapp.domain.transport.Transport;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceRequest.
 */
@Entity
@Table(name = "service_request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @GeneratedValue
    @Column(name = "id")
    private ServiceRequestId id;

    @Embedded
    @Column(name = "items")
    private ServiceRequestItems items;
    @Embedded
    @Column(name = "service_name")
    private ServiceRequestName serviceName;
    @Embedded
    @Column(name = "total_weight_of_items")
    private ServiceRequestTotalWeightOfItems totalWeightOfItems;
    @Embedded
    @Column(name = "price")
    private ServiceRequestPrice price;
    @Embedded
    @Column(name = "date")
    private ServiceRequestDate date;

    @JsonIgnoreProperties(value = { "customer", "serviceRequest", "transport" }, allowSetters = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @JsonIgnoreProperties(value = { "applicationUser", "locations", "serviceRequest" }, allowSetters = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "serviceRequest", cascade = CascadeType.ALL)
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

    public ServiceRequest(ServiceRequestId id, ServiceRequestItems items, ServiceRequestName serviceName, ServiceRequestTotalWeightOfItems totalWeightOfItems, ServiceRequestPrice price, ServiceRequestDate date, Location location, Customer customer) {
        this.id = id;
        this.items = items;
        this.serviceName = serviceName;
        this.totalWeightOfItems = totalWeightOfItems;
        this.price = price;
        this.date = date;
        this.location = location;
        this.customer = customer;
    }

    public ServiceRequest() {

    }

    public ServiceRequest(ServiceRequestId id) {
        this.id = id;
    }

    public ServiceRequest(ServiceRequest serviceRequest){
        this.id = serviceRequest.getId();
        this.items = serviceRequest.getItems();
        this.serviceName = serviceRequest.getServiceName();
        this.totalWeightOfItems = serviceRequest.getTotalWeightOfItems();
        this.price = serviceRequest.getPrice();
        this.date = serviceRequest.getDate();
        this.location = serviceRequest.getLocation();
        this.customer = serviceRequest.getCustomer();
        this.serviceStatuses = serviceRequest.getServiceStatuses();
        this.transport = serviceRequest.getTransport();
    }

    public ServiceRequestId getId() {
        return this.id != null ? new ServiceRequestId(id.value()) : null;
    }

    public ServiceRequestItems getItems() {
        return this.items != null ? new ServiceRequestItems(this.items.value()) : null;
    }

    public void updateItems(ServiceRequestItems items) {
        this.items = items != null ? new ServiceRequestItems(items.value()) : null;
    }

    public ServiceRequestName getServiceName() {
        return this.serviceName != null ? new ServiceRequestName(this.serviceName.value()) : null;
    }

    public void updateServiceName(ServiceRequestName serviceName) {
        this.serviceName = serviceName != null ? new ServiceRequestName(serviceName.value()) : null;
    }

    public ServiceRequestTotalWeightOfItems getTotalWeightOfItems() {
        return this.totalWeightOfItems != null ?
            new ServiceRequestTotalWeightOfItems(totalWeightOfItems.value()) : null;
    }

    public void updateTotalWeightOfItems(ServiceRequestTotalWeightOfItems totalWeightOfItems) {
        this.totalWeightOfItems = totalWeightOfItems != null ? new ServiceRequestTotalWeightOfItems(totalWeightOfItems.value()) : null;
    }

    public ServiceRequestPrice getPrice() {
        return this.price != null ? new ServiceRequestPrice(price.value()) : null;
    }

    public void updatePrice(ServiceRequestPrice price) {
        this.price = price != null ? new ServiceRequestPrice(price.value()) : null;
    }

    public ServiceRequestDate getDate() {
        return this.date != null ? new ServiceRequestDate(date.value()) : null;
    }

    public void updateDate(ServiceRequestDate date) {
        this.date = date != null ? new ServiceRequestDate(date.value()) : null;
    }

    public Location getLocation() {
        return this.location;
    }

    public void updateLocation(Location location) {
        this.location = location != null ? new Location(location) : null;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void updateCustomer(Customer customer) {
        this.customer = customer != null ? new Customer(customer) : null;
    }

    public Set<ServiceStatus> getServiceStatuses() {
        return this.serviceStatuses != null ? new HashSet<>(this.serviceStatuses) : new HashSet<>();
    }

    public void addServiceStatuses(ServiceStatus status){
        if(this.serviceStatuses!= null && status != null){
            this.serviceStatuses.add(status);
        }

    }

    public void updateServiceStatuses(Set<ServiceStatus> serviceStatuses) {
        if (this.serviceStatuses != null) {
            this.serviceStatuses.forEach(i -> i.updateServiceRequest(null));
        }
        if (serviceStatuses != null) {
            serviceStatuses.forEach(i -> i.updateServiceRequest(this));
        }
        this.serviceStatuses = serviceStatuses != null ? new HashSet<>(serviceStatuses) : new HashSet<>();
    }

    public void addServiceStatus(ServiceStatus serviceStatus) {
        if(this.serviceStatuses!= null && serviceStatus != null){
            serviceStatus.updateServiceRequest(this);
            ServiceStatus newServiceStatus = new ServiceStatus(serviceStatus);
            this.serviceStatuses.add(newServiceStatus);
        }


    }

    public void removeServiceStatus(ServiceStatus serviceStatus) {
        if(this.serviceStatuses!= null && serviceStatus != null) {
            this.serviceStatuses.remove(serviceStatus);
            serviceStatus.updateServiceRequest(null);
        }
    }

    public Transport getTransport() {
        return this.transport;
    }

    public void updateTransport(Transport transport) {
        if (this.transport != null) {
            this.transport.updateServiceRequest(null);
        }
        if (transport != null) {
            transport.updateServiceRequest(this);
        }
        this.transport = transport != null ? new Transport(transport) : null;
    }

    public void updateRequestStatus(ServiceStatusDTO statusDTO){
        if(this.serviceStatuses!= null && statusDTO != null){
            this.serviceStatuses.add(ServiceStatusMapper.toEntity(statusDTO));
        }
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

        String id = this.id != null ? this.id.toString() : "null";
        String items = this.items != null ? this.items.toString() : "null";
        String serviceName = this.serviceName != null ? this.serviceName.toString() : "null";
        String totalWeightOfItems = this.totalWeightOfItems != null ? this.totalWeightOfItems.toString() : "null";
        String price = this.price != null ? this.price.toString() : "null";
        String date = this.date != null ? this.date.toString() : "null";

        return "ServiceRequest{" +
            "id=" + id +
            ", items='" + items + "'" +
            ", serviceName='" + serviceName + "'" +
            ", totalWeightOfItems=" + totalWeightOfItems +
            ", price=" + price +
            ", date='" + date + "'" +
            "}";
    }
}
