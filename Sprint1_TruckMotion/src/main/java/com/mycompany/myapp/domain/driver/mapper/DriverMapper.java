package com.mycompany.myapp.domain.driver.mapper;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.truck.Truck;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.truck.dto.TruckDTO;
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
