package com.mycompany.myapp.domain.transport.mapper;

import static com.mycompany.myapp.domain.transport.TransportAsserts.*;
import static com.mycompany.myapp.domain.transport.TransportTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransportMapperTest {

    private TransportMapper transportMapper;

    @BeforeEach
    void setUp() {
        transportMapper = new TransportMapper();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTransportSample1();
        var actual = transportMapper.toEntity(transportMapper.toDto(expected));
        assertTransportAllPropertiesEquals(expected, actual);
    }
}
