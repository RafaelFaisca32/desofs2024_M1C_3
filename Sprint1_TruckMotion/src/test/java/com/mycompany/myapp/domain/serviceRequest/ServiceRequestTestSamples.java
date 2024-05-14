package com.mycompany.myapp.domain.serviceRequest;

import java.util.UUID;

public class ServiceRequestTestSamples {

    public static ServiceRequest getServiceRequestSample1() {
        return new ServiceRequest().id(new ServiceRequestId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))).items(new ServiceRequestItems("items1")).serviceName(new ServiceRequestName("serviceName1"));
    }

    public static ServiceRequest getServiceRequestSample2() {
        return new ServiceRequest().id(new ServiceRequestId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))).items(new ServiceRequestItems("items2")).serviceName(new ServiceRequestName("serviceName2"));
    }

    public static ServiceRequest getServiceRequestRandomSampleGenerator() {
        return new ServiceRequest().id(new ServiceRequestId(UUID.randomUUID())).items(new ServiceRequestItems(UUID.randomUUID().toString())).serviceName(new ServiceRequestName(UUID.randomUUID().toString()));
    }
}
