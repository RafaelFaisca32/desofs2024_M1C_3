package com.mycompany.myapp.domain.location.mapper;

import static com.mycompany.myapp.domain.location.LocationAsserts.*;
import static com.mycompany.myapp.domain.location.LocationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationMapperTest {

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getLocationRandomSampleGenerator();
        var actual = LocationMapper.toEntity(LocationMapper.toDto(expected));
        assertLocationAllPropertiesEquals(expected, actual);
    }
}
