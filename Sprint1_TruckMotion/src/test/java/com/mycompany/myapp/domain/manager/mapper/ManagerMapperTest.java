package com.mycompany.myapp.domain.manager.mapper;

import static com.mycompany.myapp.domain.manager.ManagerAsserts.*;
import static com.mycompany.myapp.domain.manager.ManagerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManagerMapperTest {

    private ManagerMapper managerMapper;

    @BeforeEach
    void setUp() {
        managerMapper = new ManagerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getManagerSample1();
        var actual = managerMapper.toEntity(managerMapper.toDto(expected));
        assertManagerAllPropertiesEquals(expected, actual);
    }
}
