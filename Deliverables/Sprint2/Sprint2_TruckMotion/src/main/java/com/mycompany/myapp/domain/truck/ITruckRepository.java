package com.mycompany.myapp.domain.truck;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITruckRepository {
    List<Truck> findAll();

    Truck save(Truck truck);

    Optional<Truck> findById(TruckId id);

    void deleteById(TruckId id);

    Truck saveAndFlush(Truck customer);

    long count();

    boolean existsById(TruckId id);
}
