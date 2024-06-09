package com.mycompany.myapp.domain.driver.mapper;

import static com.mycompany.myapp.domain.driver.DriverAsserts.*;
import static com.mycompany.myapp.domain.driver.DriverTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DriverMapperTest {


    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDriverRandomSampleGenerator();
        var actual = DriverMapper.toEntity(DriverMapper.toDto(expected));
        assertDriverAllPropertiesEquals(expected, actual);
    }
}
