package com.mycompany.myapp.domain.driver;

import java.util.UUID;

public class DriverTestSamples {

    public static Driver getDriverSample1() {
        return new Driver().id(new DriverId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")));
    }

    public static Driver getDriverSample2() {
        return new Driver().id(new DriverId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")));
    }

    public static Driver getDriverRandomSampleGenerator() {
        return new Driver().id(new DriverId(UUID.randomUUID()));
    }
}
