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

/**
 * Mapper for the entity {@link Driver} and its DTO {@link DriverDTO}.
 */
public final class DriverMapper {

    public static Driver toEntity(DriverDTO dto) {
        return null;
    }

    public static DriverDTO toDto(Driver entity) {
        return null;
    }

    public static List<Driver> toEntity(List<DriverDTO> dtoList) {
        return List.of();
    }

    public static List<DriverDTO> toDto(List<Driver> entityList) {
        return List.of();
    }

    public static void partialUpdate(Driver entity, DriverDTO dto) {

    }
}
