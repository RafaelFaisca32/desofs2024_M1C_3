package com.mycompany.myapp.domain.truck.mapper;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.truck.Truck;
import com.mycompany.myapp.domain.truck.dto.TruckDTO;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity {@link truck} and its DTO {@link TruckDTO}.
 */

public final class TruckMapper {

    public static Truck toEntity(TruckDTO dto) {
        return null;
    }

    public static TruckDTO toDto(Truck entity) {
        return null;
    }

    public static List<Truck> toEntity(List<TruckDTO> dtoList) {
        return List.of();
    }

    public static List<TruckDTO> toDto(List<Truck> entityList) {
        return List.of();
    }

    public static void partialUpdate(Truck entity, TruckDTO dto) {

    }
}
