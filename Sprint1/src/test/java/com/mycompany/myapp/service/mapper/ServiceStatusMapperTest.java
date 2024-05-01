package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.ServiceStatusAsserts.*;
import static com.mycompany.myapp.domain.ServiceStatusTestSamples.*;

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
