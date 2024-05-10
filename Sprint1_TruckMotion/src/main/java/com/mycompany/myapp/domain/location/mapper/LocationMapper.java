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

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
public final class LocationMapper {

    public static Location toEntity(LocationDTO dto) {
        return null;
    }

    public static LocationDTO toDto(Location entity) {
        return null;
    }

    public static List<Location> toEntity(List<LocationDTO> dtoList) {
        return List.of();
    }

    public static List<LocationDTO> toDto(List<Location> entityList) {
        return List.of();
    }

    public static void partialUpdate(Location entity, LocationDTO dto) {

    }
}
