package com.mycompany.myapp.domain.transport.mapper;

import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.driver.mapper.DriverMapper;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.location.mapper.LocationMapper;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.serviceRequest.mapper.ServiceRequestMapper;
import com.mycompany.myapp.domain.transport.Transport;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.domain.transport.TransportEndTime;
import com.mycompany.myapp.domain.transport.TransportId;
import com.mycompany.myapp.domain.transport.TransportStartTime;
import com.mycompany.myapp.domain.transport.dto.TransportDTO;

import java.util.List;
import java.util.Objects;

/**
 * Mapper for the entity {@link Transport} and its DTO {@link TransportDTO}.
 */
public final class TransportMapper {

    public static Transport toEntity(TransportDTO dto) {
        if(dto == null) return null;
        TransportId id = dto.getId() != null ? new TransportId(dto.getId()) : new TransportId();
        TransportStartTime startTime = new TransportStartTime(dto.getStartTime());
        TransportEndTime endTime = new TransportEndTime(dto.getEndTime());
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
        return new TransportDTO(
            entity.getId() != null ? entity.getId().value() : null,
            entity.getStartTime() != null ? entity.getStartTime().value() : null,
            entity.getEndTime() != null ? entity.getEndTime().value() : null,
            locationDTO,
            driverDTO,
            serviceRequestDTO);
    }

    public static List<Transport> toEntity(List<TransportDTO> dtoList) {
        if(dtoList == null) return List.of();
        return dtoList.stream().filter(Objects::nonNull).map(TransportMapper::toEntity).toList();
    }

    public static List<TransportDTO> toDto(List<Transport> entityList) {
        if(entityList == null) return List.of();
        return entityList.stream().filter(Objects::nonNull).map(TransportMapper::toDto).toList();
    }

    public static void partialUpdate(Transport entity, TransportDTO dto) {
        if(entity == null || dto == null) return;
        if(dto.getStartTime() != null) {
            entity.updateStartTime(new TransportStartTime(dto.getStartTime()));
        }
        if(dto.getEndTime() != null) {
            entity.updateEndTime(new TransportEndTime(dto.getEndTime()));
        }
        if(dto.getLocation() != null) {
            entity.updateLocation(LocationMapper.toEntity(dto.getLocation()));
        }
        if(dto.getDriver() != null) {
            entity.updateDriver(DriverMapper.toEntity(dto.getDriver()));
        }
        if(dto.getServiceRequest() != null){
            entity.updateServiceRequest(ServiceRequestMapper.toEntity(dto.getServiceRequest()));
        }



    }
}
