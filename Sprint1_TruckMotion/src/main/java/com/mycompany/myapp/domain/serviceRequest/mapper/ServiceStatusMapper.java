package com.mycompany.myapp.domain.serviceRequest.mapper;

import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.serviceRequest.ServiceStatus;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceStatusDTO;
import java.util.Objects;
import java.util.UUID;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceStatus} and its DTO {@link ServiceStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceStatusMapper extends EntityMapper<ServiceStatusDTO, ServiceStatus> {
    @Mapping(target = "serviceRequest", source = "serviceRequest", qualifiedByName = "serviceRequestId")
    ServiceStatusDTO toDto(ServiceStatus s);

    @Named("serviceRequestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceRequestDTO toDtoServiceRequestId(ServiceRequest serviceRequest);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
