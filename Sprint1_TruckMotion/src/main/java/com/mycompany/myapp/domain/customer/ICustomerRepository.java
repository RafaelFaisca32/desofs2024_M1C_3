package com.mycompany.myapp.domain.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICustomerRepository {
    List<Customer> findAll();

    Customer save(Customer customer);

    Optional<Customer> findById(CustomerId id);
    //Optional<Customer> findById(UUID id);

    void deleteById(CustomerId id);
    //void deleteById(UUID id);

    Customer saveAndFlush(Customer customer);

    long count();

    boolean existsById(CustomerId id);
    //boolean existsById(UUID id);
}
