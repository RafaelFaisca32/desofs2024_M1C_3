package com.mycompany.myapp.domain.truck.mapper;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.truck.Truck;
import com.mycompany.myapp.domain.truck.dto.TruckDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link truck} and its DTO {@link TruckDTO}.
 */
@Mapper(componentModel = "spring")
public interface TruckMapper extends EntityMapper<TruckDTO, Truck> {}
