package com.mycompany.myapp.domain.driver.mapper;

import com.mycompany.myapp.domain.driver.DriverId;
import com.mycompany.myapp.domain.truck.mapper.TruckMapper;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.truck.Truck;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.truck.dto.TruckDTO;

import java.util.List;
import java.util.Objects;

import com.mycompany.myapp.domain.user.mapper.ApplicationUserMapper;

/**
 * Mapper for the entity {@link Driver} and its DTO {@link DriverDTO}.
 */
public final class DriverMapper {

    public static Driver toEntity(DriverDTO dto) {
        if(dto == null) return null;

        DriverId id = dto.getId() != null ? new DriverId(dto.getId()) : new DriverId() ;

        Truck truck = TruckMapper.toEntity(dto.getTruck());

        ApplicationUser applicationUser = ApplicationUserMapper.toEntity(dto.getApplicationUser());

        return new Driver(id,truck,applicationUser);
    }

    public static DriverDTO toDto(Driver entity) {
        if(entity == null) return null;
        TruckDTO truckDTO = TruckMapper.toDto(entity.getTruck());
        ApplicationUserDTO applicationUserDTO = ApplicationUserMapper.toDto(entity.getApplicationUser());

        return new DriverDTO(
            entity.getId() != null ? entity.getId().value() : null,
            truckDTO,
            applicationUserDTO);
    }

    public static List<Driver> toEntity(List<DriverDTO> dtoList) {
        if(dtoList == null) return List.of();
        return dtoList.stream().filter(Objects::nonNull).map(DriverMapper::toEntity).toList();
    }

    public static List<DriverDTO> toDto(List<Driver> entityList) {
        if(entityList == null) return List.of();
        return entityList.stream().filter(Objects::nonNull).map(DriverMapper::toDto).toList();
    }

    public static void partialUpdate(Driver entity, DriverDTO dto) {
        if(entity == null || dto == null) return;
        if(dto.getTruck() != null){
            entity.updateTruck(TruckMapper.toEntity(dto.getTruck()));
        }
        if(dto.getApplicationUser() != null){
            entity.updateApplicationUser(entity.getApplicationUser());
        }
    }
}
