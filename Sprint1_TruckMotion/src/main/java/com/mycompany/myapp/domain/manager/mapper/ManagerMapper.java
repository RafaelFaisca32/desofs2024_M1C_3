package com.mycompany.myapp.domain.manager.mapper;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.manager.Manager;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.manager.dto.ManagerDTO;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity {@link Manager} and its DTO {@link ManagerDTO}.
 */
public final class ManagerMapper{

    public static Manager toEntity(ManagerDTO dto) {
        return null;
    }

    public static ManagerDTO toDto(Manager entity) {
        return null;
    }

    public static List<Manager> toEntity(List<ManagerDTO> dtoList) {
        return List.of();
    }

    public static List<ManagerDTO> toDto(List<Manager> entityList) {
        return List.of();
    }

    public static void partialUpdate(Manager entity, ManagerDTO dto) {

    }
}
