package com.mycompany.myapp.domain.location;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ILocationRepository {
    List<Location> findAll();

    Location save(Location location);

    Optional<Location> findById(LocationId id);

    void deleteById(LocationId id);

    Location saveAndFlush(Location location);

    long count();

    boolean existsById(LocationId id);
}
