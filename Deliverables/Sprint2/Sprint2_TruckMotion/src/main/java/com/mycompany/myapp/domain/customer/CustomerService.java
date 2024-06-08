package com.mycompany.myapp.domain.customer;

import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import com.mycompany.myapp.infrastructure.repository.jpa.CustomerRepository;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.customer.mapper.CustomerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Customer}.
 */
@Service
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final ICustomerRepository customerRepository;
    private static final String ENTITY_NAME = "customer";


    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        Customer customer = CustomerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return CustomerMapper.toDto(customer);
    }

    /**
     * Update a customer.
     *
     * @param customerDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerDTO update(CustomerDTO customerDTO) {
        CustomerId id = new CustomerId(customerDTO.getId());
        if (!customerRepository.existsById( id )) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        log.debug("Request to update Customer : {}", customerDTO);
        Customer customer = CustomerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return CustomerMapper.toDto(customer);
    }

    /**
     * Partially update a customer.
     *
     * @param customerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomerDTO> partialUpdate(CustomerDTO customerDTO) {
        log.debug("Request to partially update Customer : {}", customerDTO);
        CustomerId id = new CustomerId(customerDTO.getId());
        if (!customerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return customerRepository
            .findById(id)
            .map(existingCustomer -> {
                CustomerMapper.partialUpdate(existingCustomer, customerDTO);

                return existingCustomer;
            })
            .map(customerRepository::save)
            .map(CustomerMapper::toDto);
    }

    /**
     * Get all the customers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAll() {
        log.debug("Request to get all Customers");
        return customerRepository.findAll().stream().map(CustomerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the customers where ServiceRequest is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAllWhereServiceRequestIsNull() {
        log.debug("Request to get all customers where ServiceRequest is null");
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
            .filter(customer -> customer.getServiceRequests() == null)
            .map(CustomerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> findOne(UUID id) {
        log.debug("Request to get Customer : {}", id);
        CustomerId cId = new CustomerId(id);
        return customerRepository.findById(cId).map(CustomerMapper::toDto);
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Customer : {}", id);
        CustomerId cId = new CustomerId(id);
        customerRepository.deleteById(cId);
    }

    @Transactional(readOnly = true)
    public Optional<CustomerDTO> getByUserId(Long userId) {
        log.debug("Request to get Customer by LoggedIn User : {}", userId);
        return customerRepository.getByUserId(userId).map(CustomerMapper::toDto);
    }
}
