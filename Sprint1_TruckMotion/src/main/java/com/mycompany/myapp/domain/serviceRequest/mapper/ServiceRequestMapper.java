package com.mycompany.myapp.domain.serviceRequest.mapper;

import com.mycompany.myapp.domain.customer.mapper.CustomerMapper;
import com.mycompany.myapp.domain.location.mapper.LocationMapper;
import com.mycompany.myapp.domain.serviceRequest.*;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mycompany.myapp.domain.serviceRequest.dto.ServiceStatusDTO;
import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceRequest} and its DTO {@link ServiceRequestDTO}.
 */
public final class ServiceRequestMapper {

    public static ServiceRequest toEntity(ServiceRequestDTO dto) {
        if(dto == null) return null;
        ServiceRequestId serviceRequestId = new ServiceRequestId(dto.getId());
        ServiceRequestItems serviceRequestItems = new ServiceRequestItems(dto.getItems());
        ServiceRequestName serviceRequestName = new ServiceRequestName(dto.getServiceName());
        ServiceRequestTotalWeightOfItems serviceRequestTotalWeightOfItems = new ServiceRequestTotalWeightOfItems(dto.getTotalWeightOfItems());
        ServiceRequestPrice serviceRequestPrice = new ServiceRequestPrice(dto.getPrice());
        ServiceRequestDate serviceRequestDate = new ServiceRequestDate(dto.getDate());
        return new ServiceRequest(serviceRequestId, serviceRequestItems, serviceRequestName, serviceRequestTotalWeightOfItems, serviceRequestPrice, serviceRequestDate);
    }

    public static ServiceRequestDTO toDto(ServiceRequest entity) {
        if (entity == null) return null;
        CustomerDTO customerDTO = CustomerMapper.toDto(entity.getCustomer());
        LocationDTO locationDTO = LocationMapper.toDto(entity.getLocation());
        return new ServiceRequestDTO(
            entity.getId().value(),
            entity.getItems().value(),
            entity.getServiceName().value(),
            entity.getTotalWeightOfItems().value(),
            entity.getPrice().value(),
            entity.getDate().value(),
            locationDTO,
            customerDTO);
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
            entity.setCustomer(CustomerMapper.toEntity(dto.getCustomer()));
        }
        if(dto.getLocation() != null){
            entity.setLocation(LocationMapper.toEntity(dto.getLocation()));
        }
        if(dto.getItems() != null){
            entity.setItems(new ServiceRequestItems(dto.getItems()));
        }
        if(dto.getServiceName() != null){
            entity.setServiceName(new ServiceRequestName(dto.getServiceName()));
        }
        if(dto.getTotalWeightOfItems() != null){
            entity.setTotalWeightOfItems(new ServiceRequestTotalWeightOfItems(dto.getTotalWeightOfItems()));
        }
        if(dto.getPrice() != null){
            entity.setPrice(new ServiceRequestPrice(dto.getPrice()));
        }
        if(dto.getDate() != null){
            entity.setDate(new ServiceRequestDate(dto.getDate()));
        }
    }
}
