package com.mycompany.myapp.domain.serviceRequest.mapper;

import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.serviceRequest.ServiceStatus;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceStatusDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link ServiceStatus} and its DTO {@link ServiceStatusDTO}.
 */
@Service
public class ServiceStatusMapper implements EntityMapper<ServiceStatusDTO,ServiceStatus>{

    @Override
    public ServiceStatus toEntity(ServiceStatusDTO dto) {
        return null;
    }

    @Override
    public ServiceStatusDTO toDto(ServiceStatus entity) {
        return null;
    }

    @Override
    public List<ServiceStatus> toEntity(List<ServiceStatusDTO> dtoList) {
        return List.of();
    }

    @Override
    public List<ServiceStatusDTO> toDto(List<ServiceStatus> entityList) {
        return List.of();
    }

    @Override
    public void partialUpdate(ServiceStatus entity, ServiceStatusDTO dto) {

    }
}
