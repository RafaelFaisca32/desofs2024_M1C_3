package com.mycompany.myapp.domain.transport.mapper;

import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.transport.Transport;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.domain.transport.dto.TransportDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transport} and its DTO {@link TransportDTO}.
 */
public final class TransportMapper {

    public static Transport toEntity(TransportDTO dto) {
        return null;
    }

    public static TransportDTO toDto(Transport entity) {
        return null;
    }

    public static List<Transport> toEntity(List<TransportDTO> dtoList) {
        return List.of();
    }

    public static List<TransportDTO> toDto(List<Transport> entityList) {
        return List.of();
    }

    public static void partialUpdate(Transport entity, TransportDTO dto) {

    }
}
