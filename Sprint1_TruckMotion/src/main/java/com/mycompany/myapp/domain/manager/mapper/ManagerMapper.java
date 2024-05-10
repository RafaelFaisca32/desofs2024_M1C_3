package com.mycompany.myapp.domain.manager.mapper;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.manager.Manager;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.manager.dto.ManagerDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Mapper for the entity {@link Manager} and its DTO {@link ManagerDTO}.
 */
@Service
public class ManagerMapper implements EntityMapper<ManagerDTO,Manager>{

    @Override
    public Manager toEntity(ManagerDTO dto) {
        return null;
    }

    @Override
    public ManagerDTO toDto(Manager entity) {
        return null;
    }

    @Override
    public List<Manager> toEntity(List<ManagerDTO> dtoList) {
        return List.of();
    }

    @Override
    public List<ManagerDTO> toDto(List<Manager> entityList) {
        return List.of();
    }

    @Override
    public void partialUpdate(Manager entity, ManagerDTO dto) {

    }
}
