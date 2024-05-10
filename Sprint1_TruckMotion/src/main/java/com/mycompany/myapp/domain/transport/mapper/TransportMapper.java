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
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Transport} and its DTO {@link TransportDTO}.
 */
@Service
public class TransportMapper implements EntityMapper<TransportDTO,Transport> {

    @Override
    public Transport toEntity(TransportDTO dto) {
        return null;
    }

    @Override
    public TransportDTO toDto(Transport entity) {
        return null;
    }

    @Override
    public List<Transport> toEntity(List<TransportDTO> dtoList) {
        return List.of();
    }

    @Override
    public List<TransportDTO> toDto(List<Transport> entityList) {
        return List.of();
    }

    @Override
    public void partialUpdate(Transport entity, TransportDTO dto) {

    }
}
