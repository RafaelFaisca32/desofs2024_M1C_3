package com.mycompany.myapp.domain.truck.mapper;

import static com.mycompany.myapp.domain.truck.TruckAsserts.*;
import static com.mycompany.myapp.domain.truck.TruckTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TruckMapperTest {

    private TruckMapper truckMapper;

    @BeforeEach
    void setUp() {
        truckMapper = new TruckMapper();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTruckSample1();
        var actual = truckMapper.toEntity(truckMapper.toDto(expected));
        assertTruckAllPropertiesEquals(expected, actual);
    }
}
