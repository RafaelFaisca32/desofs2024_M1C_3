package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ApplicationUser;
import com.mycompany.myapp.domain.Manager;
import com.mycompany.myapp.service.dto.ApplicationUserDTO;
import com.mycompany.myapp.service.dto.ManagerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Manager} and its DTO {@link ManagerDTO}.
 */
@Mapper(componentModel = "spring")
public interface ManagerMapper extends EntityMapper<ManagerDTO, Manager> {
    @Mapping(target = "applicationUser", source = "applicationUser", qualifiedByName = "applicationUserId")
    ManagerDTO toDto(Manager s);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);
}
