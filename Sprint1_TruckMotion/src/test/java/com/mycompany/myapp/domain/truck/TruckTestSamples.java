package com.mycompany.myapp.domain.truck;

import com.mycompany.myapp.domain.transport.TransportId;

import java.util.UUID;

public class TruckTestSamples {

    public static Truck getTruckSample1() {
        return new Truck().id(new TruckId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))).make(new Make("make1")).model(new Model("model1"));
    }

    public static Truck getTruckSample2() {
        return new Truck().id(new TruckId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))).make(new Make("make2")).model(new Model("model2"));
    }

    public static Truck getTruckRandomSampleGenerator() {
        return new Truck().id(new TruckId(UUID.randomUUID())).make(new Make(UUID.randomUUID().toString())).model(new Model(UUID.randomUUID().toString()));
    }
}
