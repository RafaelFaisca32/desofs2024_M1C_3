package com.mycompany.myapp.domain.user;

import java.util.UUID;

public class AuthorityTestSamples {

    public static Authority getAuthoritySample1() {
        return new Authority("name1");
    }

    public static Authority getAuthoritySample2() {
        return new Authority("name2");
    }

    public static Authority getAuthorityRandomSampleGenerator() {
        return new Authority(UUID.randomUUID().toString());
    }
}
