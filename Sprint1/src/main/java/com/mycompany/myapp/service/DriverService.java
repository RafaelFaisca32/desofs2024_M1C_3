package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DriverDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Driver}.
 */
public interface DriverService {
    /**
     * Save a driver.
     *
     * @param driverDTO the entity to save.
     * @return the persisted entity.
     */
    DriverDTO save(DriverDTO driverDTO);

    /**
     * Updates a driver.
     *
     * @param driverDTO the entity to update.
     * @return the persisted entity.
     */
    DriverDTO update(DriverDTO driverDTO);

    /**
     * Partially updates a driver.
     *
     * @param driverDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DriverDTO> partialUpdate(DriverDTO driverDTO);

    /**
     * Get all the drivers.
     *
     * @return the list of entities.
     */
    List<DriverDTO> findAll();

    /**
     * Get all the DriverDTO where Transport is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DriverDTO> findAllWhereTransportIsNull();

    /**
     * Get the "id" driver.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DriverDTO> findOne(UUID id);

    /**
     * Delete the "id" driver.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
