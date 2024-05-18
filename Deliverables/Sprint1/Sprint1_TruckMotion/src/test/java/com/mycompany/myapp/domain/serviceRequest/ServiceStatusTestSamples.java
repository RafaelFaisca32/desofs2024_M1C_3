package com.mycompany.myapp.domain.serviceRequest;

import java.time.*;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ServiceStatusTestSamples {

    public static ServiceStatus getServiceStatusSample1() {
        ServiceStatusId serviceStatusId = new ServiceStatusId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
        ServiceRequestDate serviceRequestDate = new ServiceRequestDate(getZonedDateTime(2024,5,17,10,0,0));
        ServiceStatusObservations serviceStatusObservations = new ServiceStatusObservations("observations1");
        Status status = Status.PENDING;
        ServiceRequest serviceRequest = ServiceRequestTestSamples.getServiceRequestSample1();
        return new ServiceStatus(serviceStatusId, serviceRequestDate, serviceStatusObservations, status, serviceRequest);
    }

    public static ServiceStatus getServiceStatusSample2() {
        ServiceStatusId serviceStatusId = new ServiceStatusId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
        ServiceRequestDate serviceRequestDate = new ServiceRequestDate(getZonedDateTime(2024,5,17,10,0,0));
        ServiceStatusObservations serviceStatusObservations = new ServiceStatusObservations("observations2");
        Status status = Status.PENDING;
        ServiceRequest serviceRequest = ServiceRequestTestSamples.getServiceRequestSample2();
        return new ServiceStatus(serviceStatusId, serviceRequestDate, serviceStatusObservations, status, serviceRequest);
    }

    public static ServiceStatus getServiceStatusRandomSampleGenerator() {
        ServiceStatusId serviceStatusId = new ServiceStatusId(UUID.randomUUID());
        ServiceRequestDate serviceRequestDate = new ServiceRequestDate(getRandomZonedDateTime());
        ServiceStatusObservations serviceStatusObservations = new ServiceStatusObservations(UUID.randomUUID().toString());
        Status status = Status.PENDING;
        ServiceRequest serviceRequest = ServiceRequestTestSamples.getServiceRequestRandomSampleGenerator();
        return new ServiceStatus(serviceStatusId, serviceRequestDate, serviceStatusObservations, status, serviceRequest);
    }

    public static ZonedDateTime getRandomZonedDateTime(){
        LocalDateTime startDateTime = LocalDateTime.of(1970, 1, 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2100, 1, 1, 0, 0);

        long startEpochSecond = startDateTime.toEpochSecond(ZoneId.of("UTC").getRules().getOffset(startDateTime));
        long endEpochSecond = endDateTime.toEpochSecond(ZoneId.of("UTC").getRules().getOffset(endDateTime));

        long randomEpochSecond = ThreadLocalRandom.current().nextLong(startEpochSecond, endEpochSecond);

        LocalDateTime randomDateTime = LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneId.of("UTC").getRules().getOffset(startDateTime));

        ZoneId zoneId = ZoneId.systemDefault();

        return randomDateTime.atZone(zoneId);
    }

    public static ZonedDateTime getZonedDateTime(int year, int month, int day, int hour, int minute, int second){
        LocalDate date = LocalDate.of(year, month, day );
        LocalTime time = LocalTime.of(hour, minute,second);
        ZoneId zone = ZoneId.of("Europe/Lisbon");

        return ZonedDateTime.of(date, time, zone);
    }
}
