package com.mycompany.myapp.domain.driver;

import com.mycompany.myapp.domain.truck.Truck;
import com.mycompany.myapp.domain.truck.TruckTestSamples;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.user.ApplicationUserTestSamples;

import java.util.UUID;

public class DriverTestSamples {

    public static Driver getDriverSample1() {
        DriverId id = new DriverId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
        Truck truck = TruckTestSamples.getTruckSample1();
        ApplicationUser applicationUser = ApplicationUserTestSamples.getApplicationUserSample1();
        return new Driver(id,truck,applicationUser);
    }

    public static Driver getDriverSample2() {
        DriverId id = new DriverId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
        Truck truck = TruckTestSamples.getTruckSample2();
        ApplicationUser applicationUser = ApplicationUserTestSamples.getApplicationUserSample2();
        return new Driver(id,truck,applicationUser);
    }

    public static Driver getDriverRandomSampleGenerator() {
        DriverId id = new DriverId();
        Truck truck = TruckTestSamples.getTruckRandomSampleGenerator();
        ApplicationUser applicationUser = ApplicationUserTestSamples.getApplicationUserRandomSampleGenerator();
        return new Driver(id,truck,applicationUser);

    }
}
