package com.mycompany.myapp.domain.transport;

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
}
