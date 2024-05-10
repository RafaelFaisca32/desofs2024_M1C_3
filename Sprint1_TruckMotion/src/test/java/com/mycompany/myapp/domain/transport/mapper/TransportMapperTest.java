package com.mycompany.myapp.domain.transport.mapper;

import static com.mycompany.myapp.domain.transport.TransportAsserts.*;
import static com.mycompany.myapp.domain.transport.TransportTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransportMapperTest {

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTransportSample1();
        var actual = TransportMapper.toEntity(TransportMapper.toDto(expected));
        assertTransportAllPropertiesEquals(expected, actual);
    }
}
