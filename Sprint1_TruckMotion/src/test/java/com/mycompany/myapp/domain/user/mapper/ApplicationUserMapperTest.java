package com.mycompany.myapp.domain.user.mapper;

import static com.mycompany.myapp.domain.user.ApplicationUserAsserts.*;
import static com.mycompany.myapp.domain.user.ApplicationUserTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationUserMapperTest {

    private ApplicationUserMapper applicationUserMapper;

    @BeforeEach
    void setUp() {
        applicationUserMapper = new ApplicationUserMapper();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getApplicationUserSample1();
        var actual = applicationUserMapper.toEntity(applicationUserMapper.toDto(expected));
        assertApplicationUserAllPropertiesEquals(expected, actual);
    }
}
