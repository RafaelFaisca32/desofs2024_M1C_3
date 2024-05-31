package com.mycompany.myapp.infrastructure.repository.jpa;

import com.mycompany.myapp.domain.customer.Customer;
import java.util.UUID;

import com.mycompany.myapp.domain.customer.CustomerId;
import com.mycompany.myapp.domain.customer.ICustomerRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, CustomerId>, ICustomerRepository {}
