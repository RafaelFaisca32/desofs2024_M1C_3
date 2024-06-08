package com.mycompany.myapp.domain.transport;

import com.mycompany.myapp.domain.location.Location;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITransportRepository {
    List<Transport> findAll();

    Transport save(Transport transport);

    Optional<Transport> findById(TransportId id);

    void deleteById(TransportId id);

    Transport saveAndFlush(Transport transport);

    long count();

    boolean existsById(TransportId id);

    List<Object[]> findFreeDrivers(TransportEndTime startDate, TransportStartTime endDate);

    List<Transport> getByUserId(Long userId);
}
