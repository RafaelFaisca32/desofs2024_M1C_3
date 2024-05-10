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

import com.mycompany.myapp.domain.user.mapper.ApplicationUserMapper;

/**
 * Mapper for the entity {@link Driver} and its DTO {@link DriverDTO}.
 */
public final class DriverMapper {

    public static Driver toEntity(DriverDTO dto) {
        DriverId id = new DriverId(dto.getId());
        Truck truck = TruckMapper.toEntity(dto.getTruck());
        ApplicationUser applicationUser = ApplicationUserMapper.toEntity(dto.getApplicationUser());
        return new Driver(id,truck,applicationUser);
    }

    public static DriverDTO toDto(Driver entity) {
        TruckDTO truckDTO = TruckMapper.toDto(entity.getTruck());
        ApplicationUserDTO applicationUserDTO = ApplicationUserMapper.toDto(entity.getApplicationUser());
        return new DriverDTO(entity.getId().value(),truckDTO,applicationUserDTO);
    }

    public static List<Driver> toEntity(List<DriverDTO> dtoList) {
        return dtoList.stream().map(DriverMapper::toEntity).toList();
    }

    public static List<DriverDTO> toDto(List<Driver> entityList) {
        return entityList.stream().map(DriverMapper::toDto).toList();
    }

    public static void partialUpdate(Driver entity, DriverDTO dto) {
        if(dto.getTruck() != null){
            entity.truck(entity.getTruck());
        }
        if(dto.getApplicationUser() != null){
            entity.applicationUser(entity.getApplicationUser());
        }
    }
}
