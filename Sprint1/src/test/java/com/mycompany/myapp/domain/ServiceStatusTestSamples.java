package com.mycompany.myapp.domain;

import java.util.UUID;

public class ServiceStatusTestSamples {

    public static ServiceStatus getServiceStatusSample1() {
        return new ServiceStatus().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).observations("observations1");
    }

    public static ServiceStatus getServiceStatusSample2() {
        return new ServiceStatus().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).observations("observations2");
    }

    public static ServiceStatus getServiceStatusRandomSampleGenerator() {
        return new ServiceStatus().id(UUID.randomUUID()).observations(UUID.randomUUID().toString());
    }
}
