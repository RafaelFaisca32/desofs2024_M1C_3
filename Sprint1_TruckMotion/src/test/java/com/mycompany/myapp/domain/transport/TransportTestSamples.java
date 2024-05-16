package com.mycompany.myapp.domain.transport;

import com.mycompany.myapp.domain.truck.TruckId;

import java.util.UUID;

public class TransportTestSamples {

    public static Transport getTransportSample1() {
        TransportId id = new TransportId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));

        return new Transport().id(id);
    }

    public static Transport getTransportSample2() {
        return new Transport().id(new TransportId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")));
    }

    public static Transport getTransportRandomSampleGenerator() {
        return new Transport().id(new TransportId());
    }
}
