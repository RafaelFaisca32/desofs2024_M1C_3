package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.domain.Location;
import com.mycompany.myapp.service.dto.CustomerDTO;
import com.mycompany.myapp.service.dto.LocationDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    LocationDTO toDto(Location s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
