package com.mycompany.myapp.domain.customer;

import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.user.ApplicationUserTestSamples;
import org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

import java.util.UUID;

public class CustomerTestSamples {

    public static Customer getCustomerSample1() {
        CustomerId id = new CustomerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
        Company company = new Company("company1");
        ApplicationUser applicationUser = ApplicationUserTestSamples.getApplicationUserSample1();
        return new Customer(id,company,applicationUser);
    }

    public static Customer getCustomerSample2() {
        CustomerId id = new CustomerId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
        Company company = new Company("company2");
        ApplicationUser applicationUser = ApplicationUserTestSamples.getApplicationUserSample2();
        return new Customer(id,company,applicationUser);

    }

    public static Customer getCustomerRandomSampleGenerator() {
        CustomerId id = new CustomerId();
        Company company = new Company(RandomStringUtils.randomAlphabetic(10));
        ApplicationUser applicationUser = ApplicationUserTestSamples.getApplicationUserRandomSampleGenerator();
        return new Customer(id,company,applicationUser);
    }
}
