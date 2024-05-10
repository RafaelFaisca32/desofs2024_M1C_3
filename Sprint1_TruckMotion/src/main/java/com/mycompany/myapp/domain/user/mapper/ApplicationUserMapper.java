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
@Service
public class ApplicationUserMapper implements EntityMapper<ApplicationUserDTO, ApplicationUser> {

    @Override
    public ApplicationUser toEntity(ApplicationUserDTO dto) {
        return null;
    }

    @Override
    public ApplicationUserDTO toDto(ApplicationUser entity) {
        return null;
    }

    @Override
    public List<ApplicationUser> toEntity(List<ApplicationUserDTO> dtoList) {
        return List.of();
    }

    @Override
    public List<ApplicationUserDTO> toDto(List<ApplicationUser> entityList) {
        return List.of();
    }

    @Override
    public void partialUpdate(ApplicationUser entity, ApplicationUserDTO dto) {

    }
}
