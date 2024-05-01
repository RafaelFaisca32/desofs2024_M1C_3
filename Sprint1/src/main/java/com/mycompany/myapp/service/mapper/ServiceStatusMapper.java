package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Service;
import com.mycompany.myapp.domain.ServiceStatus;
import com.mycompany.myapp.service.dto.ServiceDTO;
import com.mycompany.myapp.service.dto.ServiceStatusDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceStatus} and its DTO {@link ServiceStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceStatusMapper extends EntityMapper<ServiceStatusDTO, ServiceStatus> {
    @Mapping(target = "service", source = "service", qualifiedByName = "serviceId")
    ServiceStatusDTO toDto(ServiceStatus s);

    @Named("serviceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceDTO toDtoServiceId(Service service);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
