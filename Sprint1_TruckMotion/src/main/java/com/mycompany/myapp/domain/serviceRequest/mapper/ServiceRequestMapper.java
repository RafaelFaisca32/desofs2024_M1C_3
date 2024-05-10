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
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link ServiceRequest} and its DTO {@link ServiceRequestDTO}.
 */
@Service
public class ServiceRequestMapper implements EntityMapper<ServiceRequestDTO,ServiceRequest> {

    @Override
    public ServiceRequest toEntity(ServiceRequestDTO dto) {
        return null;
    }

    @Override
    public ServiceRequestDTO toDto(ServiceRequest entity) {
        return null;
    }

    @Override
    public List<ServiceRequest> toEntity(List<ServiceRequestDTO> dtoList) {
        return List.of();
    }

    @Override
    public List<ServiceRequestDTO> toDto(List<ServiceRequest> entityList) {
        return List.of();
    }

    @Override
    public void partialUpdate(ServiceRequest entity, ServiceRequestDTO dto) {

    }
}
