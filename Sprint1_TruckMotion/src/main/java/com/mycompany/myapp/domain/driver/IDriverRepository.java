package com.mycompany.myapp.domain.driver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDriverRepository {
    List<Driver> findAll();

    Driver save(Driver driver);

    Optional<Driver> findById(UUID id);

    void deleteById(UUID id);

    Driver saveAndFlush(Driver driver);

    long count();

    boolean existsById(UUID id);
}
