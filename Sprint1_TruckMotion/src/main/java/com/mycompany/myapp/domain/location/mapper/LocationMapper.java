package com.mycompany.myapp.domain.location.mapper;

import com.mycompany.myapp.domain.customer.Company;
import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.customer.CustomerId;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.location.dto.LocationDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Service
public class LocationMapper implements EntityMapper<LocationDTO,Location> {

    @Override
    public Location toEntity(LocationDTO dto) {
        return null;
    }

    @Override
    public LocationDTO toDto(Location entity) {
        return null;
    }

    @Override
    public List<Location> toEntity(List<LocationDTO> dtoList) {
        return List.of();
    }

    @Override
    public List<LocationDTO> toDto(List<Location> entityList) {
        return List.of();
    }

    @Override
    public void partialUpdate(Location entity, LocationDTO dto) {

    }
}
