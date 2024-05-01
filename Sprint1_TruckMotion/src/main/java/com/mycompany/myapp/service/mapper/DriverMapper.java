package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ApplicationUser;
import com.mycompany.myapp.domain.Driver;
import com.mycompany.myapp.domain.Truck;
import com.mycompany.myapp.service.dto.ApplicationUserDTO;
import com.mycompany.myapp.service.dto.DriverDTO;
import com.mycompany.myapp.service.dto.TruckDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Driver} and its DTO {@link DriverDTO}.
 */
@Mapper(componentModel = "spring")
public interface DriverMapper extends EntityMapper<DriverDTO, Driver> {
    @Mapping(target = "truck", source = "truck", qualifiedByName = "truckId")
    @Mapping(target = "applicationUser", source = "applicationUser", qualifiedByName = "applicationUserId")
    DriverDTO toDto(Driver s);

    @Named("truckId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TruckDTO toDtoTruckId(Truck truck);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
