package com.mycompany.myapp.domain.location;

import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.customer.CustomerTestSamples;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class LocationTestSamples {

    public static Location getLocationSample1() {
        LocationId id = new LocationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
        GeographicalCoordinates coord = new GeographicalCoordinates(1F,2F,3F);
        Customer customer = CustomerTestSamples.getCustomerSample1();
        return new Location(id,coord,customer);
    }

    public static Location getLocationSample2() {
        LocationId id = new LocationId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
        GeographicalCoordinates coord = new GeographicalCoordinates(4F,5F,6F);
        Customer customer = CustomerTestSamples.getCustomerSample2();
        return new Location(id,coord,customer);
    }

    public static Location getLocationRandomSampleGenerator() {
        LocationId id = new LocationId();
        Random r = new SecureRandom();
        int min = 1;
        int max = 100;
        float f1 = min + r.nextFloat() * (max - min);
        float f2 = min + r.nextFloat() * (max - min);
        float f3 = min + r.nextFloat() * (max - min);
        GeographicalCoordinates coord = new GeographicalCoordinates(f1,f2,f3);
        Customer customer = CustomerTestSamples.getCustomerRandomSampleGenerator();
        return new Location(id,coord,customer);
    }
}
