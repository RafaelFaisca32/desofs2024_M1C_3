package com.mycompany.myapp.domain.serviceRequest.mapper;

import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.customer.mapper.CustomerMapper;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.location.mapper.LocationMapper;
import com.mycompany.myapp.domain.serviceRequest.*;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceStatusDTO;

import java.util.*;
import java.util.stream.Collectors;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceStatus} and its DTO {@link ServiceStatusDTO}.
 */
public final class ServiceStatusMapper {

    public static ServiceStatus toEntity(ServiceStatusDTO dto) {
        ServiceStatusId serviceStatusId = new ServiceStatusId(dto.getId());
        ServiceRequestDate serviceRequestDate = new ServiceRequestDate(dto.getDate());
        ServiceStatusObservations serviceStatusObservations = new ServiceStatusObservations(dto.getObservations());
        ServiceRequest serviceRequest = ServiceRequestMapper.toEntity(dto.getServiceRequest());
        return new ServiceStatus(serviceStatusId, serviceRequestDate, serviceStatusObservations, dto.getStatus(), serviceRequest);
    }

    public static ServiceStatusDTO toDto(ServiceStatus entity) {
        if (entity == null) return null;
        ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO(entity.getServiceRequest().getId().value());
        return new ServiceStatusDTO(
            entity.getId().value(),
            entity.getDate().value(),
            entity.getObservations().value(),
            entity.getStatus(),
            serviceRequestDTO);
    }

    public static List<ServiceStatus> toEntity(List<ServiceStatusDTO> dtoList) {
        return dtoList.stream().map(dto -> {
                try {
                    return ServiceStatusMapper.toEntity(dto);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .filter(entity -> entity != null)
            .collect(Collectors.toList());
    }

    public static List<ServiceStatusDTO> toDto(List<ServiceStatus> entityList) {
        return entityList.stream().map(entity -> {
                try {
                    return ServiceStatusMapper.toDto(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .filter(entity -> entity != null)
            .collect(Collectors.toList());
    }

    public static void partialUpdate(ServiceStatus entity, ServiceStatusDTO dto) {
        if(dto.getServiceRequest() != null){
            entity.setServiceRequest(ServiceRequestMapper.toEntity(dto.getServiceRequest()));
        }
        if(dto.getDate() != null){
            entity.setDate(new ServiceRequestDate(dto.getDate()));
        }
        if(dto.getObservations() != null){
            entity.setObservations(new ServiceStatusObservations(dto.getObservations()));
        }
        if(dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
    }
}
