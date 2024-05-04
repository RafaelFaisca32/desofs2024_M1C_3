package com.mycompany.myapp.domain.transport.dto;

import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.domain.transport.Transport;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link Transport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransportDTO implements Serializable {

    private UUID id;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private LocationDTO location;

    private DriverDTO driver;

    private ServiceRequestDTO serviceRequest;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public DriverDTO getDriver() {
        return driver;
    }

    public void setDriver(DriverDTO driver) {
        this.driver = driver;
    }

    public ServiceRequestDTO getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequestDTO serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransportDTO)) {
            return false;
        }

        TransportDTO transportDTO = (TransportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransportDTO{" +
            "id='" + getId() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", location=" + getLocation() +
            ", driver=" + getDriver() +
            ", serviceRequest=" + getServiceRequest() +
            "}";
    }
}
