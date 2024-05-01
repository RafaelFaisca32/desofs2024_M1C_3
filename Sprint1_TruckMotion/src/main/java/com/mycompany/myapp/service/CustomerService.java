package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.repository.CustomerRepository;
import com.mycompany.myapp.service.dto.CustomerDTO;
import com.mycompany.myapp.service.mapper.CustomerMapper;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Customer}.
 */
@Service
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    /**
     * Update a customer.
     *
     * @param customerDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerDTO update(CustomerDTO customerDTO) {
        log.debug("Request to update Customer : {}", customerDTO);
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    /**
     * Partially update a customer.
     *
     * @param customerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomerDTO> partialUpdate(CustomerDTO customerDTO) {
        log.debug("Request to partially update Customer : {}", customerDTO);

        return customerRepository
            .findById(customerDTO.getId())
            .map(existingCustomer -> {
                customerMapper.partialUpdate(existingCustomer, customerDTO);

                return existingCustomer;
            })
            .map(customerRepository::save)
            .map(customerMapper::toDto);
    }

    /**
     * Get all the customers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAll() {
        log.debug("Request to get all Customers");
        return customerRepository.findAll().stream().map(customerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the customers where ServiceRequest is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAllWhereServiceRequestIsNull() {
        log.debug("Request to get all customers where ServiceRequest is null");
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
            .filter(customer -> customer.getServiceRequest() == null)
            .map(customerMapper::toDto)
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
        return customerRepository.findById(id).map(customerMapper::toDto);
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }
}
