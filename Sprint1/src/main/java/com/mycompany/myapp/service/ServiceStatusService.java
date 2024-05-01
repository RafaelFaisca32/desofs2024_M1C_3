package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ServiceStatusDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ServiceStatus}.
 */
public interface ServiceStatusService {
    /**
     * Save a serviceStatus.
     *
     * @param serviceStatusDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceStatusDTO save(ServiceStatusDTO serviceStatusDTO);

    /**
     * Updates a serviceStatus.
     *
     * @param serviceStatusDTO the entity to update.
     * @return the persisted entity.
     */
    ServiceStatusDTO update(ServiceStatusDTO serviceStatusDTO);

    /**
     * Partially updates a serviceStatus.
     *
     * @param serviceStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceStatusDTO> partialUpdate(ServiceStatusDTO serviceStatusDTO);

    /**
     * Get all the serviceStatuses.
     *
     * @return the list of entities.
     */
    List<ServiceStatusDTO> findAll();

    /**
     * Get the "id" serviceStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceStatusDTO> findOne(UUID id);

    /**
     * Delete the "id" serviceStatus.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
