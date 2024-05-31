package com.mycompany.myapp.domain.truck;

import com.mycompany.myapp.domain.transport.TransportId;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class TruckTestSamples {

    public static Truck getTruckSample1() {
        TruckId id = new TruckId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
        Make make = new Make("make1");
        Model model = new Model("model1");
        return new Truck(id,make,model);
    }

    public static Truck getTruckSample2() {
        TruckId id = new TruckId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
        Make make = new Make("make2");
        Model model = new Model("model2");
        return new Truck(id,make,model);
    }

    public static Truck getTruckRandomSampleGenerator() {
        TruckId id = new TruckId();
        Make make = new Make(RandomStringUtils.randomAlphabetic(10));
        Model model = new Model(RandomStringUtils.randomAlphabetic(10));
        return new Truck(id,make,model);
    }
}
