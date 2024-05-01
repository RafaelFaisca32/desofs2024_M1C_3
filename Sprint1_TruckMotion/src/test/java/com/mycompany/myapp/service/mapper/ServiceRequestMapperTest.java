package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.ServiceRequestAsserts.*;
import static com.mycompany.myapp.domain.ServiceRequestTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceRequestMapperTest {

    private ServiceRequestMapper serviceRequestMapper;

    @BeforeEach
    void setUp() {
        serviceRequestMapper = new ServiceRequestMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getServiceRequestSample1();
        var actual = serviceRequestMapper.toEntity(serviceRequestMapper.toDto(expected));
        assertServiceRequestAllPropertiesEquals(expected, actual);
    }
}
