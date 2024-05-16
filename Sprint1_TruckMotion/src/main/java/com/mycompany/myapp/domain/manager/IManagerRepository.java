package com.mycompany.myapp.domain.manager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IManagerRepository {
    List<Manager> findAll();

    Manager save(Manager customer);

    Optional<Manager> findById(ManagerId id);

    void deleteById(ManagerId id);

    Manager saveAndFlush(Manager manager);

    long count();

    boolean existsById(UUID id);
}
