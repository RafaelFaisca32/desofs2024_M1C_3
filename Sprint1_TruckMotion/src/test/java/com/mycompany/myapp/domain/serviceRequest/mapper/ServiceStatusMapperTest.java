package com.mycompany.myapp.domain.serviceRequest.mapper;

import static com.mycompany.myapp.domain.serviceRequest.ServiceStatusAsserts.*;
import static com.mycompany.myapp.domain.serviceRequest.ServiceStatusTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceStatusMapperTest {

    private ServiceStatusMapper serviceStatusMapper;

    @BeforeEach
    void setUp() {
        serviceStatusMapper = new ServiceStatusMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getServiceStatusSample1();
        var actual = serviceStatusMapper.toEntity(serviceStatusMapper.toDto(expected));
        assertServiceStatusAllPropertiesEquals(expected, actual);
    }
}
