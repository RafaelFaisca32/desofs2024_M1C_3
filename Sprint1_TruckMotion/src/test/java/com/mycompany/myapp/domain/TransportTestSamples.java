package com.mycompany.myapp.domain;

import java.util.UUID;

public class TransportTestSamples {

    public static Transport getTransportSample1() {
        return new Transport().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static Transport getTransportSample2() {
        return new Transport().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static Transport getTransportRandomSampleGenerator() {
        return new Transport().id(UUID.randomUUID());
    }
}
