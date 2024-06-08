package com.mycompany.myapp.domain.location.mapper;

import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.customer.mapper.CustomerMapper;
import com.mycompany.myapp.domain.location.GeographicalCoordinates;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.location.LocationId;
import com.mycompany.myapp.domain.location.dto.LocationDTO;

import java.util.List;
import java.util.Objects;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
public final class LocationMapper {

    public static Location toEntity(LocationDTO dto) {
        if(dto == null) return null;

        LocationId id = dto.getId() != null ? new LocationId(dto.getId()) : new LocationId();

        GeographicalCoordinates coord =
            new GeographicalCoordinates(dto.getCoordX(),dto.getCoordY(),dto.getCoordZ());

        Customer customer = CustomerMapper.toEntity(dto.getCustomer());

        return new Location(id,coord,customer);
    }

    public static LocationDTO toDto(Location entity) {
        if(entity == null) return null;
        CustomerDTO cDTO = CustomerMapper.toDto(entity.getCustomer());
        return new LocationDTO(
            entity.getId() != null ? entity.getId().value() : null,
            entity.getCoord() != null ? entity.getCoord().xValue() : null,
            entity.getCoord() != null ? entity.getCoord().yValue() : null,
            entity.getCoord() != null ? entity.getCoord().zValue() : null,
            cDTO);

    }

    public static List<Location> toEntity(List<LocationDTO> dtoList) {
        if(dtoList == null) return List.of();
        return dtoList.stream().filter(Objects::nonNull).map(LocationMapper::toEntity).toList();
    }

    public static List<LocationDTO> toDto(List<Location> entityList) {
        if(entityList == null) return List.of();
        return entityList.stream().filter(Objects::nonNull).map(LocationMapper::toDto).toList();
    }

    public static void partialUpdate(Location entity, LocationDTO dto) {
        if(entity == null || dto == null) return;
        if(dto.getCustomer() != null){
            entity.updateCustomer(CustomerMapper.toEntity(dto.getCustomer()));
        }
        if(dto.getCoordX() != null && dto.getCoordY() != null && dto.getCoordZ() != null){
            entity.updateCoord(new GeographicalCoordinates(dto.getCoordX(),dto.getCoordY(),dto.getCoordZ()));
        }

    }
}
