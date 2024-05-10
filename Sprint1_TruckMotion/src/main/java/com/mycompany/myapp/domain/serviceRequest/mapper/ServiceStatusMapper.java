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

/**
 * Mapper for the entity {@link ServiceStatus} and its DTO {@link ServiceStatusDTO}.
 */
public final class ServiceStatusMapper {

    public static ServiceStatus toEntity(ServiceStatusDTO dto) {
        return null;
    }

    public static ServiceStatusDTO toDto(ServiceStatus entity) {
        return null;
    }

    public static List<ServiceStatus> toEntity(List<ServiceStatusDTO> dtoList) {
        return List.of();
    }

    public static List<ServiceStatusDTO> toDto(List<ServiceStatus> entityList) {
        return List.of();
    }

    public static void partialUpdate(ServiceStatus entity, ServiceStatusDTO dto) {

    }
}
