package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.dto.CustomerDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    CustomerDTO toDto(Customer s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
