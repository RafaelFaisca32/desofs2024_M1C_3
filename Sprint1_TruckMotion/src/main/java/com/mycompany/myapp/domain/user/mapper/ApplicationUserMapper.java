package com.mycompany.myapp.domain.user.mapper;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.user.User;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.user.dto.UserDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Mapper for the entity {@link ApplicationUser} and its DTO {@link ApplicationUserDTO}.
 */

public final class ApplicationUserMapper{

    public static ApplicationUser toEntity(ApplicationUserDTO dto) {
        return new ApplicationUser();
    }

    public static ApplicationUserDTO toDto(ApplicationUser entity) {
        return new ApplicationUserDTO();
    }

    public static List<ApplicationUser> toEntity(List<ApplicationUserDTO> dtoList) {
        return List.of();
    }

    public static List<ApplicationUserDTO> toDto(List<ApplicationUser> entityList) {
        return List.of();
    }

    public static void partialUpdate(ApplicationUser entity, ApplicationUserDTO dto) {

    }
}
