package com.mycompany.myapp.domain.customer.mapper;

import com.mycompany.myapp.domain.customer.Company;
import com.mycompany.myapp.domain.customer.CustomerId;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.user.mapper.ApplicationUserMapper;

import java.util.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */

public final class CustomerMapper {

    public static Customer toEntity(CustomerDTO dto) {
        if(dto == null) return null;

        CustomerId id = dto.getId() != null ? new CustomerId(dto.getId()) : new CustomerId();

        Company company = new Company(dto.getCompany());

        ApplicationUser applicationUser = ApplicationUserMapper.toEntity(dto.getApplicationUser());

        return new Customer(id,company,applicationUser);
    }

    public static CustomerDTO toDto(Customer entity) {
        if(entity == null) return null;
        ApplicationUserDTO applicationUserDTO = ApplicationUserMapper.toDto(entity.getApplicationUser());

        Set<LocationDTO> locations = new HashSet<>();
        if (!entity.getLocations().isEmpty()){
            for (Location location : entity.getLocations())
            {
                locations.add(new LocationDTO(location.getId().value(), location.getCoord().xValue(), location.getCoord().yValue(), location.getCoord().zValue(), null));
            }
        }

        return new CustomerDTO(
            entity.getId() != null ? entity.getId().value() : null,
            entity.getCompany() != null ? entity.getCompany().value() : null,
            applicationUserDTO, locations);
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
            entity.updateCompany(new Company(dto.getCompany()));
        }
        if(dto.getApplicationUser() != null){
            entity.setApplicationUser(ApplicationUserMapper.toEntity(dto.getApplicationUser()));
        }
    }
}
