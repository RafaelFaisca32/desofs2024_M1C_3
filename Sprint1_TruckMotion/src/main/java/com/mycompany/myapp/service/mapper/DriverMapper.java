package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Driver;
import com.mycompany.myapp.domain.Truck;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.dto.DriverDTO;
import com.mycompany.myapp.service.dto.TruckDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Driver} and its DTO {@link DriverDTO}.
 */
@Mapper(componentModel = "spring")
public interface DriverMapper extends EntityMapper<DriverDTO, Driver> {
    @Mapping(target = "truck", source = "truck", qualifiedByName = "truckId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    DriverDTO toDto(Driver s);

    @Named("truckId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TruckDTO toDtoTruckId(Truck truck);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
