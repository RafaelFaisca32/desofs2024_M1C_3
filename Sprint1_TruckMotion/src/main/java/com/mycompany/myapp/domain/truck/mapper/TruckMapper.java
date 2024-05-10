package com.mycompany.myapp.domain.truck.mapper;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.truck.Truck;
import com.mycompany.myapp.domain.truck.dto.TruckDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Mapper for the entity {@link truck} and its DTO {@link TruckDTO}.
 */
@Service
public class TruckMapper implements EntityMapper<TruckDTO, Truck> {

    @Override
    public Truck toEntity(TruckDTO dto) {
        return null;
    }

    @Override
    public TruckDTO toDto(Truck entity) {
        return null;
    }

    @Override
    public List<Truck> toEntity(List<TruckDTO> dtoList) {
        return List.of();
    }

    @Override
    public List<TruckDTO> toDto(List<Truck> entityList) {
        return List.of();
    }

    @Override
    public void partialUpdate(Truck entity, TruckDTO dto) {

    }
}
