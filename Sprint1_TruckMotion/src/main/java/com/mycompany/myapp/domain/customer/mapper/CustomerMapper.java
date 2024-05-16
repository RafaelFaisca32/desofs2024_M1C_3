package com.mycompany.myapp.domain.customer.mapper;

import com.mycompany.myapp.domain.customer.Company;
import com.mycompany.myapp.domain.customer.CustomerId;
import com.mycompany.myapp.domain.shared.mapper.EntityMapper;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.user.mapper.ApplicationUserMapper;
import org.mapstruct.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */

public final class CustomerMapper {

    public static Customer toEntity(CustomerDTO dto) {
        if(dto == null) return null;
        CustomerId id = new CustomerId(dto.getId());
        Company company = new Company(dto.getCompany());
        ApplicationUser applicationUser = ApplicationUserMapper.toEntity(dto.getApplicationUser());
        return new Customer(id,company,applicationUser);
    }

    public static CustomerDTO toDto(Customer entity) {
        if(entity == null) return null;
        ApplicationUserDTO applicationUserDTO = ApplicationUserMapper.toDto(entity.getApplicationUser());
        return new CustomerDTO(entity.getId().value(),entity.getCompany().value(),applicationUserDTO);
    }

    public static List<Customer> toEntity(List<CustomerDTO> dtoList) {
        if(dtoList == null) return List.of();
        return dtoList.stream().filter(Objects::nonNull).map(CustomerMapper::toEntity).toList();
    }

    public static List<CustomerDTO> toDto(List<Customer> entityList) {
        if(entityList == null) return List.of();
        return entityList.stream().filter(Objects::nonNull).map(CustomerMapper::toDto).toList();
    }

    public static void partialUpdate(Customer entity, CustomerDTO dto) {
        if(entity == null || dto == null) return;
        if(dto.getCompany() != null){
            entity.setCompany(new Company(dto.getCompany()));
        }
        if(dto.getApplicationUser() != null){
            entity.setApplicationUser(ApplicationUserMapper.toEntity(dto.getApplicationUser()));
        }
    }
}
