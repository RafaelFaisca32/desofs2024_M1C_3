package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ApplicationUser;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.dto.ApplicationUserDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationUser} and its DTO {@link ApplicationUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicationUserMapper extends EntityMapper<ApplicationUserDTO, ApplicationUser> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userLogin")
    ApplicationUserDTO toDto(ApplicationUser s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
