package com.mycompany.myapp.domain.truck;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITruckRepository {
    List<Truck> findAll();

    Truck save(Truck truck);

    Optional<Truck> findById(UUID id);

    void deleteById(UUID id);

    Truck saveAndFlush(Truck customer);

    long count();

    boolean existsById(UUID id);
}
