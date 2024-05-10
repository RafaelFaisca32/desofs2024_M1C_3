package com.mycompany.myapp.domain.driver.mapper;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.truck.Truck;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.truck.dto.TruckDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Driver} and its DTO {@link DriverDTO}.
 */
@Service
public class DriverMapper implements EntityMapper<DriverDTO,Driver>{

    @Override
    public Driver toEntity(DriverDTO dto) {
        return null;
    }

    @Override
    public DriverDTO toDto(Driver entity) {
        return null;
    }

    @Override
    public List<Driver> toEntity(List<DriverDTO> dtoList) {
        return List.of();
    }

    @Override
    public List<DriverDTO> toDto(List<Driver> entityList) {
        return List.of();
    }

    @Override
    public void partialUpdate(Driver entity, DriverDTO dto) {

    }
}
