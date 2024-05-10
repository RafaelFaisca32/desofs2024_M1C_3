package com.mycompany.myapp.domain.manager.mapper;

import static com.mycompany.myapp.domain.manager.ManagerAsserts.*;
import static com.mycompany.myapp.domain.manager.ManagerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManagerMapperTest {

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getManagerSample1();
        var actual = ManagerMapper.toEntity(ManagerMapper.toDto(expected));
        assertManagerAllPropertiesEquals(expected, actual);
    }
}
