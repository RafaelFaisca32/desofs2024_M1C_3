package com.mycompany.myapp.domain.manager;

import java.util.UUID;

public class ManagerTestSamples {

    public static Manager getManagerSample1() {
        return new Manager().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static Manager getManagerSample2() {
        return new Manager().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static Manager getManagerRandomSampleGenerator() {
        return new Manager().id(UUID.randomUUID());
    }
}
