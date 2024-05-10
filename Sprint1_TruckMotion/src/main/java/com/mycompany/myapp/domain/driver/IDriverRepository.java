package com.mycompany.myapp.domain.driver;

import java.util.List;
import java.util.Optional;

public interface IDriverRepository {
    List<Driver> findAll();

    Driver save(Driver driver);

    Optional<Driver> findById(DriverId id);

    void deleteById(DriverId id);

    Driver saveAndFlush(Driver driver);

    long count();

    boolean existsById(DriverId id);
}
