package com.mycompany.myapp.domain.customer;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class CustomerTestSamples {

    public static Customer getCustomerSample1() {
        return new Customer().id( new CustomerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))).company(new Company("company1"));
    }

    public static Customer getCustomerSample2() {
        return new Customer().id(new CustomerId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))).company(new Company("company2"));
    }

    public static Customer getCustomerRandomSampleGenerator() {
        return new Customer().id(new CustomerId(UUID.randomUUID())).company(new Company(RandomStringUtils.randomAlphabetic(10)));
    }
}
