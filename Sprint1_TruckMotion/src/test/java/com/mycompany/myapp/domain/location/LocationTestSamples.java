package com.mycompany.myapp.domain.location;

import java.util.UUID;

public class LocationTestSamples {

    public static Location getLocationSample1() {
        return new Location().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static Location getLocationSample2() {
        return new Location().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static Location getLocationRandomSampleGenerator() {
        return new Location().id(UUID.randomUUID());
    }
}
