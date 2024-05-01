package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.domain.Location;
import com.mycompany.myapp.domain.Service;
import com.mycompany.myapp.service.dto.CustomerDTO;
import com.mycompany.myapp.service.dto.LocationDTO;
import com.mycompany.myapp.service.dto.ServiceDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Service} and its DTO {@link ServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceMapper extends EntityMapper<ServiceDTO, Service> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    ServiceDTO toDto(Service s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
