package com.mycompany.myapp.domain.serviceRequest.mapper;

import com.mycompany.myapp.domain.serviceRequest.*;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceStatusDTO;

import java.util.*;

/**
 * Mapper for the entity {@link ServiceStatus} and its DTO {@link ServiceStatusDTO}.
 */
public final class ServiceStatusMapper {

    public static ServiceStatus toEntity(ServiceStatusDTO dto) {
        if(dto == null) return null;
        ServiceStatusId serviceStatusId =
            dto.getId() != null ? new ServiceStatusId(dto.getId()) : new ServiceStatusId();
        ServiceRequestDate serviceRequestDate =
            new ServiceRequestDate(dto.getDate());
        ServiceStatusObservations serviceStatusObservations =
            new ServiceStatusObservations(dto.getObservations());
        ServiceRequest serviceRequest =
            new ServiceRequest(new ServiceRequestId(dto.getServiceRequest().getId()), new ServiceRequestItems(),new ServiceRequestName(), new ServiceRequestTotalWeightOfItems(), new ServiceRequestPrice(), new ServiceRequestDate());

        return new ServiceStatus(serviceStatusId, serviceRequestDate, serviceStatusObservations, dto.getStatus(), serviceRequest);
    }

    public static ServiceStatusDTO toDto(ServiceStatus entity) {
        if (entity == null) return null;
        ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO(entity.getServiceRequest().getId().value());
        return new ServiceStatusDTO(
            entity.getId() != null ? entity.getId().value() : null,
            entity.getDate() != null ? entity.getDate().value() : null,
            entity.getObservations() != null ? entity.getObservations().value() : null,
            entity.getStatus(),
            serviceRequestDTO);
    }

    public static List<ServiceStatus> toEntity(List<ServiceStatusDTO> dtoList) {
        return dtoList.stream().filter(Objects::nonNull).map(ServiceStatusMapper::toEntity).toList();

    }

    public static List<ServiceStatusDTO> toDto(List<ServiceStatus> entityList) {
        return entityList.stream().filter(Objects::nonNull).map(ServiceStatusMapper::toDto).toList();
    }

    public static void partialUpdate(ServiceStatus entity, ServiceStatusDTO dto) {
        if(entity == null || dto == null) return;

        if(dto.getServiceRequest() != null){
            entity.updateServiceRequest(ServiceRequestMapper.toEntity(dto.getServiceRequest()));
        }
        if(dto.getDate() != null){
            entity.updateDate(new ServiceRequestDate(dto.getDate()));
        }
        if(dto.getObservations() != null){
            entity.updateObservations(new ServiceStatusObservations(dto.getObservations()));
        }
    }
}
