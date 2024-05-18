package com.mycompany.myapp.domain.customer.mapper;

import static com.mycompany.myapp.domain.customer.CustomerAsserts.*;
import static com.mycompany.myapp.domain.customer.CustomerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerMapperTest {

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCustomerSample1();
        var actual = CustomerMapper.toEntity(CustomerMapper.toDto(expected));

        assertCustomerAllPropertiesEquals(expected, actual);
    }
}
