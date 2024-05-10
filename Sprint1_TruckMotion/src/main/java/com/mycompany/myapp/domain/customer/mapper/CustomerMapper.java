package com.mycompany.myapp.domain.customer.mapper;

import com.mycompany.myapp.domain.customer.Company;
import com.mycompany.myapp.domain.customer.CustomerId;
import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Service
public class CustomerMapper implements EntityMapper<CustomerDTO,Customer> {

    @Override
    public Customer toEntity(CustomerDTO dto) {
        return null;
    }

    @Override
    public CustomerDTO toDto(Customer entity) {
        return null;
    }

    @Override
    public List<Customer> toEntity(List<CustomerDTO> dtoList) {
        return List.of();
    }

    @Override
    public List<CustomerDTO> toDto(List<Customer> entityList) {
        return List.of();
    }

    @Override
    public void partialUpdate(Customer entity, CustomerDTO dto) {

    }
}
