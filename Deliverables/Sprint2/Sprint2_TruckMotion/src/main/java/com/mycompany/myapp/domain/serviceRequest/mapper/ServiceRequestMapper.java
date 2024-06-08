package com.mycompany.myapp.domain.serviceRequest.mapper;

import com.mycompany.myapp.domain.customer.Company;
import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.customer.CustomerId;
import com.mycompany.myapp.domain.customer.mapper.CustomerMapper;
import com.mycompany.myapp.domain.location.GeographicalCoordinates;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.location.LocationId;
import com.mycompany.myapp.domain.location.mapper.LocationMapper;
import com.mycompany.myapp.domain.serviceRequest.*;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;

import java.util.*;

import com.mycompany.myapp.domain.serviceRequest.dto.ServiceStatusDTO;
import com.mycompany.myapp.domain.user.ApplicationUser;

/**
 * Mapper for the entity {@link ServiceRequest} and its DTO {@link ServiceRequestDTO}.
 */
public final class ServiceRequestMapper {

    public static ServiceRequest toEntity(ServiceRequestDTO dto) {
        if(dto == null) return null;
        ServiceRequestId serviceRequestId =
            dto.getId() != null ? new ServiceRequestId(dto.getId()) : new ServiceRequestId();
        ServiceRequestItems serviceRequestItems =
            new ServiceRequestItems(dto.getItems());
        ServiceRequestName serviceRequestName =
            new ServiceRequestName(dto.getServiceName());
        ServiceRequestTotalWeightOfItems serviceRequestTotalWeightOfItems =
            new ServiceRequestTotalWeightOfItems(dto.getTotalWeightOfItems());
        ServiceRequestPrice serviceRequestPrice =
            new ServiceRequestPrice(dto.getPrice());
        ServiceRequestDate serviceRequestDate =
            new ServiceRequestDate(dto.getDate());

        Location location = LocationMapper.toEntity(dto.getLocation());
        Customer customer = CustomerMapper.toEntity(dto.getCustomer());

        return new ServiceRequest(serviceRequestId, serviceRequestItems, serviceRequestName, serviceRequestTotalWeightOfItems, serviceRequestPrice, serviceRequestDate, location, customer);
    }

    public static ServiceRequestDTO toDto(ServiceRequest entity) {
        if (entity == null) return null;
        CustomerDTO customerDTO = CustomerMapper.toDto(entity.getCustomer());
        LocationDTO locationDTO = LocationMapper.toDto(entity.getLocation());

        ServiceStatusDTO serviceStatusDTO = null;

        if (entity.getServiceStatuses() != null && !entity.getServiceStatuses().isEmpty()) {
            List<ServiceStatus> statusList = new ArrayList<>(entity.getServiceStatuses());
            statusList.sort(Comparator.comparing(status -> ((ServiceStatus) status).getDate().value()).reversed());
            serviceStatusDTO = ServiceStatusMapper.toDto(statusList.get(0));
        }

        return new ServiceRequestDTO(
            entity.getId() != null ? entity.getId().value() : null,
            entity.getItems() != null ? entity.getItems().value() : null,
            entity.getServiceName() != null ? entity.getServiceName().value() : null,
            entity.getTotalWeightOfItems() != null ? entity.getTotalWeightOfItems().value() : null,
            entity.getPrice() != null ? entity.getPrice().value() : null,
            entity.getDate() != null ? entity.getDate().value() : null,
            locationDTO,
            customerDTO,
            serviceStatusDTO
        );
    }

    public static List<ServiceRequest> toEntity(List<ServiceRequestDTO> dtoList) {
        if(dtoList == null) return List.of();
        return dtoList.stream().filter(Objects::nonNull).map(ServiceRequestMapper::toEntity).toList();

    }

    public static List<ServiceRequestDTO> toDto(List<ServiceRequest> entityList) {
        if(entityList == null) return List.of();
        return entityList.stream().filter(Objects::nonNull).map(ServiceRequestMapper::toDto).toList();
    }

    public static void partialUpdate(ServiceRequest entity, ServiceRequestDTO dto) {
        if(entity == null || dto == null ) return;
        if(dto.getCustomer() != null){
            entity.updateCustomer(new Customer(new CustomerId(dto.getCustomer().getId()),new Company(), new ApplicationUser()));
        }
        if(dto.getLocation() != null){
            entity.updateLocation(new Location(new LocationId(dto.getLocation().getId()),new GeographicalCoordinates(),new Customer()));
        }
        if(dto.getItems() != null){
            entity.updateItems(new ServiceRequestItems(dto.getItems()));
        }
        if(dto.getServiceName() != null){
            entity.updateServiceName(new ServiceRequestName(dto.getServiceName()));
        }
        if(dto.getTotalWeightOfItems() != null){
            entity.updateTotalWeightOfItems(new ServiceRequestTotalWeightOfItems(dto.getTotalWeightOfItems()));
        }
        if(dto.getPrice() != null){
            entity.updatePrice(new ServiceRequestPrice(dto.getPrice()));
        }
        if(dto.getDate() != null){
            entity.updateDate(new ServiceRequestDate(dto.getDate()));
        }
    }
}
