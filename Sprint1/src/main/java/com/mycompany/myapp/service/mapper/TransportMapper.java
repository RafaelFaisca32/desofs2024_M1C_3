package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Driver;
import com.mycompany.myapp.domain.Location;
import com.mycompany.myapp.domain.Service;
import com.mycompany.myapp.domain.Transport;
import com.mycompany.myapp.service.dto.DriverDTO;
import com.mycompany.myapp.service.dto.LocationDTO;
import com.mycompany.myapp.service.dto.ServiceDTO;
import com.mycompany.myapp.service.dto.TransportDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transport} and its DTO {@link TransportDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransportMapper extends EntityMapper<TransportDTO, Transport> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    @Mapping(target = "driver", source = "driver", qualifiedByName = "driverId")
    @Mapping(target = "service", source = "service", qualifiedByName = "serviceId")
    TransportDTO toDto(Transport s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("driverId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DriverDTO toDtoDriverId(Driver driver);

    @Named("serviceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceDTO toDtoServiceId(Service service);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
