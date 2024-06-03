package com.mycompany.myapp.domain.user.mapper;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.user.ApplicationUserBirthDate;
import com.mycompany.myapp.domain.user.ApplicationUserId;
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
        /*
        ApplicationUserId id,
                           ApplicationUserBirthDate birthDate,
                           Gender gender,
                           User internalUser
         */
        if(dto == null) return null;
        UserMapper userMapper = new UserMapper();

        ApplicationUserId uuidId = dto.getUuidId() != null ? new ApplicationUserId(dto.getUuidId()) : new ApplicationUserId();
        ApplicationUserBirthDate birthDate = dto.getBirthDate() != null ? new ApplicationUserBirthDate(dto.getBirthDate()) : null;

        return new ApplicationUser(
            dto.getId(),
            uuidId,
            birthDate,
            dto.getGender(),
            userMapper.userFromDTO(dto.getInternalUser())
        );
    }

    public static ApplicationUserDTO toDto(ApplicationUser entity) {
        //TODO
        if(entity == null) return null;
        UserMapper userMapper = new UserMapper();

        return new ApplicationUserDTO(
            entity.getId(),
            entity.getUuidId() != null ? entity.getUuidId().value() : null,
            entity.getBirthDate() != null ? entity.getBirthDate().value() : null,
            entity.getGender(),
            userMapper.userToUserDTO(entity.getInternalUser())
        );
    }

    public static List<ApplicationUser> toEntity(List<ApplicationUserDTO> dtoList) {
        if(dtoList == null) return List.of();
        return dtoList.stream().filter(Objects::nonNull).map(ApplicationUserMapper::toEntity).toList();
    }

    public static List<ApplicationUserDTO> toDto(List<ApplicationUser> entityList) {
        if(entityList == null) return List.of();
        return entityList.stream().filter(Objects::nonNull).map(ApplicationUserMapper::toDto).toList();
    }

    public static void partialUpdate(ApplicationUser entity, ApplicationUserDTO dto) {
        if(entity == null || dto == null) return;
        if(dto.getBirthDate() != null){
            entity.setBirthDate(new ApplicationUserBirthDate(dto.getBirthDate()));
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
