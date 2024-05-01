package com.mycompany.myapp.domain;

import java.util.UUID;

public class TruckTestSamples {

    public static Truck getTruckSample1() {
        return new Truck().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).make("make1").model("model1");
    }

    public static Truck getTruckSample2() {
        return new Truck().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).make("make2").model("model2");
    }

    public static Truck getTruckRandomSampleGenerator() {
        return new Truck().id(UUID.randomUUID()).make(UUID.randomUUID().toString()).model(UUID.randomUUID().toString());
    }
}
