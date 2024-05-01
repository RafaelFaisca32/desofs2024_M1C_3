package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CustomerDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Customer}.
 */
public interface CustomerService {
    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerDTO save(CustomerDTO customerDTO);

    /**
     * Updates a customer.
     *
     * @param customerDTO the entity to update.
     * @return the persisted entity.
     */
    CustomerDTO update(CustomerDTO customerDTO);

    /**
     * Partially updates a customer.
     *
     * @param customerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustomerDTO> partialUpdate(CustomerDTO customerDTO);

    /**
     * Get all the customers.
     *
     * @return the list of entities.
     */
    List<CustomerDTO> findAll();

    /**
     * Get all the CustomerDTO where Service is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustomerDTO> findAllWhereServiceIsNull();

    /**
     * Get the "id" customer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerDTO> findOne(UUID id);

    /**
     * Delete the "id" customer.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
