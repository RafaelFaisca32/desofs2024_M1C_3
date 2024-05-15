package com.mycompany.myapp.domain.serviceRequest.dto;

import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ServiceRequest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceRequestDTO implements Serializable {

    private UUID id;

    private String items;

    private String serviceName;

    private Float totalWeightOfItems;

    private Float price;

    private ZonedDateTime date;

    private LocationDTO location;

    private CustomerDTO customer;

    private ServiceStatusDTO status;

    public ServiceRequestDTO(UUID id, String items, String serviceName, Float totalWeightOfItems, Float price, ZonedDateTime date, LocationDTO location, CustomerDTO customer) {
        this.id = id;
        this.items = items;
        this.serviceName = serviceName;
        this.totalWeightOfItems = totalWeightOfItems;
        this.price = price;
        this.date = date;
        this.location = location;
        this.customer = customer;
    }

    public ServiceRequestDTO(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Float getTotalWeightOfItems() {
        return totalWeightOfItems;
    }

    public void setTotalWeightOfItems(Float totalWeightOfItems) {
        this.totalWeightOfItems = totalWeightOfItems;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public ServiceStatusDTO getStatus() {
        return status;
    }

    public void setStatus(ServiceStatusDTO status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceRequestDTO)) {
            return false;
        }

        ServiceRequestDTO serviceRequestDTO = (ServiceRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceRequestDTO{" +
            "id='" + getId() + "'" +
            ", items='" + getItems() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", totalWeightOfItems=" + getTotalWeightOfItems() +
            ", price=" + getPrice() +
            ", date='" + getDate() + "'" +
            ", location=" + getLocation() +
            ", customer=" + getCustomer() +
            ", status=" + getStatus() +
            "}";
    }
}
