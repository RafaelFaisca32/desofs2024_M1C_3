package com.mycompany.myapp.domain;

import java.util.UUID;

public class ServiceTestSamples {

    public static Service getServiceSample1() {
        return new Service().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).items("items1").serviceName("serviceName1");
    }

    public static Service getServiceSample2() {
        return new Service().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).items("items2").serviceName("serviceName2");
    }

    public static Service getServiceRandomSampleGenerator() {
        return new Service().id(UUID.randomUUID()).items(UUID.randomUUID().toString()).serviceName(UUID.randomUUID().toString());
    }
}
