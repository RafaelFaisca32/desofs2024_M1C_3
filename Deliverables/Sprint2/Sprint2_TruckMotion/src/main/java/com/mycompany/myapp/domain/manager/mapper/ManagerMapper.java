package com.mycompany.myapp.domain.manager.mapper;

import com.mycompany.myapp.domain.manager.ManagerId;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.manager.Manager;
import com.mycompany.myapp.domain.manager.dto.ManagerDTO;
import com.mycompany.myapp.domain.user.mapper.ApplicationUserMapper;

import java.util.List;
import java.util.Objects;

/**
 * Mapper for the entity {@link Manager} and its DTO {@link ManagerDTO}.
 */
public final class ManagerMapper{

    public static Manager toEntity(ManagerDTO dto) {
        if(dto == null) return null;
        ManagerId id = dto.getId() != null ? new ManagerId(dto.getId()) : new ManagerId();
        ApplicationUser applicationUser = ApplicationUserMapper.toEntity(dto.getApplicationUser());
        return new Manager(id,applicationUser);
    }

    public static ManagerDTO toDto(Manager entity) {
        if(entity == null) return null;
        return new ManagerDTO(
            entity.getId() != null ? entity.getId().value() : null,
            ApplicationUserMapper.toDto(entity.getApplicationUser()));
    }

    public static List<Manager> toEntity(List<ManagerDTO> dtoList) {
        if (dtoList == null) return List.of();
        return dtoList.stream().filter(Objects::nonNull).map(ManagerMapper::toEntity).toList();
    }

    public static List<ManagerDTO> toDto(List<Manager> entityList) {
        if (entityList == null) return List.of();
        return entityList.stream().filter(Objects::nonNull).map(ManagerMapper::toDto).toList();
    }

    public static void partialUpdate(Manager entity, ManagerDTO dto) {
        if(entity == null || dto == null) return;
        if(dto.getApplicationUser() != null){
            entity.updateApplicationUser(ApplicationUserMapper.toEntity(dto.getApplicationUser()));
        }
    }
}
