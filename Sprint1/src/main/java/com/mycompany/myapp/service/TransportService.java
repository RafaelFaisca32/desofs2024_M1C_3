package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TransportDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Transport}.
 */
public interface TransportService {
    /**
     * Save a transport.
     *
     * @param transportDTO the entity to save.
     * @return the persisted entity.
     */
    TransportDTO save(TransportDTO transportDTO);

    /**
     * Updates a transport.
     *
     * @param transportDTO the entity to update.
     * @return the persisted entity.
     */
    TransportDTO update(TransportDTO transportDTO);

    /**
     * Partially updates a transport.
     *
     * @param transportDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TransportDTO> partialUpdate(TransportDTO transportDTO);

    /**
     * Get all the transports.
     *
     * @return the list of entities.
     */
    List<TransportDTO> findAll();

    /**
     * Get the "id" transport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransportDTO> findOne(UUID id);

    /**
     * Delete the "id" transport.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
