package com.mycompany.myapp.domain.truck.mapper;

import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.driver.DriverId;
import com.mycompany.myapp.domain.driver.mapper.DriverMapper;
import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.truck.Make;
import com.mycompany.myapp.domain.truck.Model;
import com.mycompany.myapp.domain.truck.Truck;
import com.mycompany.myapp.domain.truck.TruckId;
import com.mycompany.myapp.domain.truck.dto.TruckDTO;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.user.mapper.ApplicationUserMapper;
import org.mapstruct.*;

import java.util.List;
import java.util.Objects;

/**
 * Mapper for the entity {@link truck} and its DTO {@link TruckDTO}.
 */

public final class TruckMapper {

    public static Truck toEntity(TruckDTO dto) {
        if(dto == null) return null;
        TruckId id = new TruckId(dto.getId());
        Make make = new Make(dto.getMake());
        Model model = new Model(dto.getModel());
        return new Truck(id,make,model);
    }

    public static TruckDTO toDto(Truck entity) {
        if(entity == null) return null;
        return new TruckDTO(entity.getId().value(),entity.getMake().getMake(),entity.getModel().getModel());
    }

    public static List<Truck> toEntity(List<TruckDTO> dtoList) {
        if(dtoList == null) return List.of();
        return dtoList.stream().filter(Objects::nonNull).map(TruckMapper::toEntity).toList();
    }

    public static List<TruckDTO> toDto(List<Truck> entityList) {
        if(entityList == null) List.of();
        return entityList.stream().filter(Objects::nonNull).map(TruckMapper::toDto).toList();
    }

    public static void partialUpdate(Truck entity, TruckDTO dto) {
        if(dto.getMake() != null){
            entity.setMake(new Make(dto.getMake()));
        }
        if(dto.getModel() != null){
            entity.setModel(new Model(dto.getModel()));
        }
    }
}
