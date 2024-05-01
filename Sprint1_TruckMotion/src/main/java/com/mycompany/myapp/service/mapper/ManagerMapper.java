package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Manager;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.dto.ManagerDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Manager} and its DTO {@link ManagerDTO}.
 */
@Mapper(componentModel = "spring")
public interface ManagerMapper extends EntityMapper<ManagerDTO, Manager> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    ManagerDTO toDto(Manager s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
