package com.mycompany.myapp.domain.transport.mapper;

import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.driver.DriverId;
import com.mycompany.myapp.domain.driver.mapper.DriverMapper;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.location.mapper.LocationMapper;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.serviceRequest.mapper.ServiceRequestMapper;
import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.transport.Transport;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.domain.transport.TransportId;
import com.mycompany.myapp.domain.transport.dto.TransportDTO;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transport} and its DTO {@link TransportDTO}.
 */
public final class TransportMapper {

    public static Transport toEntity(TransportDTO dto) {
        TransportId id = new TransportId(dto.getId());
        ZonedDateTime startTime = dto.getStartTime();
        ZonedDateTime endTime = dto.getEndTime();
        Location location = LocationMapper.toEntity(dto.getLocation());
        Driver driver = DriverMapper.toEntity(dto.getDriver());
        ServiceRequest serviceRequest = ServiceRequestMapper.toEntity(dto.getServiceRequest());
        return new Transport(id,startTime,endTime,location,driver,serviceRequest);
    }

    public static TransportDTO toDto(Transport entity) {
        if(entity == null) return null;
        LocationDTO locationDTO = LocationMapper.toDto(entity.getLocation());
        DriverDTO driverDTO = DriverMapper.toDto(entity.getDriver());
        ServiceRequestDTO serviceRequestDTO = ServiceRequestMapper.toDto(entity.getServiceRequest());
        return new TransportDTO(entity.getId().value(),entity.getStartTime(),entity.getEndTime(),locationDTO,driverDTO,serviceRequestDTO);
    }

    public static List<Transport> toEntity(List<TransportDTO> dtoList) {
        return dtoList.stream().map(TransportMapper::toEntity).toList();
    }

    public static List<TransportDTO> toDto(List<Transport> entityList) {
        return entityList.stream().map(TransportMapper::toDto).toList();
    }

    public static void partialUpdate(Transport entity, TransportDTO dto) {
        if(dto.getStartTime() != null) {
            entity.setStartTime(dto.getStartTime());
        }
        if(dto.getEndTime() != null) {
            entity.setEndTime(dto.getEndTime());
        }
        if(entity.getLocation() != null) {
            
        }



    }
}
