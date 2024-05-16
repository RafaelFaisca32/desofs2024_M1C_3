package com.mycompany.myapp.domain.user.mapper;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.user.User;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.user.dto.UserDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Mapper for the entity {@link ApplicationUser} and its DTO {@link ApplicationUserDTO}.
 */

public final class ApplicationUserMapper{

    public static ApplicationUser toEntity(ApplicationUserDTO dto) {
        //TODO
        if(dto == null) return null;
        UserMapper userMapper = new UserMapper();
        return new ApplicationUser(dto.getId(),dto.getName(),dto.getBirthDate(),dto.getEmail(),dto.getGender(), userMapper.userFromDTO(dto.getInternalUser()) );
    }

    public static ApplicationUserDTO toDto(ApplicationUser entity) {
        //TODO
        if(entity == null) return null;
        UserMapper userMapper = new UserMapper();
        return new ApplicationUserDTO(entity.getId(),entity.getName(),entity.getBirthDate(),entity.getEmail(),entity.getGender(),userMapper.userToUserDTO(entity.getInternalUser()));
    }

    public static List<ApplicationUser> toEntity(List<ApplicationUserDTO> dtoList) {
        //TODO
        if(dtoList == null) return List.of();
        return dtoList.stream().filter(Objects::nonNull).map(ApplicationUserMapper::toEntity).toList();
    }

    public static List<ApplicationUserDTO> toDto(List<ApplicationUser> entityList) {
        //TODO
        if(entityList == null) return List.of();
        return entityList.stream().filter(Objects::nonNull).map(ApplicationUserMapper::toDto).toList();
    }

    public static void partialUpdate(ApplicationUser entity, ApplicationUserDTO dto) {
        //TODO
        if(entity == null || dto == null) return;
        if(dto.getName() != null){
            entity.setName(dto.getName());
        }
        if(dto.getBirthDate() != null){
            entity.setBirthDate(dto.getBirthDate());
        }
        if(dto.getEmail() != null){
            entity.setEmail(dto.getEmail());
        }
        if(dto.getGender() != null){
            entity.setGender(dto.getGender());
        }
        UserMapper userMapper = new UserMapper();
        if(dto.getInternalUser()!= null){
            entity.setInternalUser(userMapper.userFromId(dto.getInternalUser().getId()));
        }
    }
}
