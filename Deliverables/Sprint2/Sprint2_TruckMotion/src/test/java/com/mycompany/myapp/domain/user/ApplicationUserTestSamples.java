package com.mycompany.myapp.domain.user;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ApplicationUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static ApplicationUser getApplicationUserSample1() {

        return new ApplicationUser(1L,new ApplicationUserId(UUID.fromString("6d232e43-bf74-4885-98e7-572cb76243df")));
    }

    public static ApplicationUser getApplicationUserSample2() {
        return new ApplicationUser(2L,new ApplicationUserId(UUID.fromString("727e0286-8bbe-45e5-9402-f8e8ece80435")));
    }

    public static ApplicationUser getApplicationUserRandomSampleGenerator() {
        return new ApplicationUser(longCount.incrementAndGet(),new ApplicationUserId(UUID.randomUUID()));
    }
}
