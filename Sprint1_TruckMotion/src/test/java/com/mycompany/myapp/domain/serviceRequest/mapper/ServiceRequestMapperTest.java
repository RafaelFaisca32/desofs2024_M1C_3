package com.mycompany.myapp.domain.serviceRequest.mapper;

import static com.mycompany.myapp.domain.serviceRequest.ServiceRequestAsserts.*;
import static com.mycompany.myapp.domain.serviceRequest.ServiceRequestTestSamples.*;

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
