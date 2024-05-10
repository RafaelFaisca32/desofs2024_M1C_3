package com.mycompany.myapp.domain.serviceRequest.mapper;

import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceRequest} and its DTO {@link ServiceRequestDTO}.
 */
public final class ServiceRequestMapper {

    public static ServiceRequest toEntity(ServiceRequestDTO dto) {
        return null;
    }

    public static ServiceRequestDTO toDto(ServiceRequest entity) {
        return null;
    }

    public static List<ServiceRequest> toEntity(List<ServiceRequestDTO> dtoList) {
        return List.of();
    }

    public static List<ServiceRequestDTO> toDto(List<ServiceRequest> entityList) {
        return List.of();
    }

    public static void partialUpdate(ServiceRequest entity, ServiceRequestDTO dto) {

    }
}
