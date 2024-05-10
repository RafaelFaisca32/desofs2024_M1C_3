package com.mycompany.myapp.domain.truck.mapper;

import static com.mycompany.myapp.domain.truck.TruckAsserts.*;
import static com.mycompany.myapp.domain.truck.TruckTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TruckMapperTest {
    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTruckSample1();
        var actual = TruckMapper.toEntity(TruckMapper.toDto(expected));
        assertTruckAllPropertiesEquals(expected, actual);
    }
}
