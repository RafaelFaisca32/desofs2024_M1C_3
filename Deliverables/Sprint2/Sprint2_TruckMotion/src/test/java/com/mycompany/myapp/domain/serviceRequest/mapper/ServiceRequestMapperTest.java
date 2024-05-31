package com.mycompany.myapp.domain.serviceRequest.mapper;

import static com.mycompany.myapp.domain.serviceRequest.ServiceRequestAsserts.*;
import static com.mycompany.myapp.domain.serviceRequest.ServiceRequestTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceRequestMapperTest {

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getServiceRequestSample1();
        var actual = ServiceRequestMapper.toEntity(ServiceRequestMapper.toDto(expected));
        assertServiceRequestAllPropertiesEquals(expected, actual);
    }
}
