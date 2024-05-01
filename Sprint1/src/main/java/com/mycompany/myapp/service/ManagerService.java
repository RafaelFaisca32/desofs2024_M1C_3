package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ManagerDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Manager}.
 */
public interface ManagerService {
    /**
     * Save a manager.
     *
     * @param managerDTO the entity to save.
     * @return the persisted entity.
     */
    ManagerDTO save(ManagerDTO managerDTO);

    /**
     * Updates a manager.
     *
     * @param managerDTO the entity to update.
     * @return the persisted entity.
     */
    ManagerDTO update(ManagerDTO managerDTO);

    /**
     * Partially updates a manager.
     *
     * @param managerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ManagerDTO> partialUpdate(ManagerDTO managerDTO);

    /**
     * Get all the managers.
     *
     * @return the list of entities.
     */
    List<ManagerDTO> findAll();

    /**
     * Get the "id" manager.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ManagerDTO> findOne(UUID id);

    /**
     * Delete the "id" manager.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
