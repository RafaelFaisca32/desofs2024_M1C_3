package com.mycompany.myapp.domain.transport;

import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.driver.DriverTestSamples;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.location.LocationTestSamples;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequestTestSamples;
import com.mycompany.myapp.domain.truck.TruckId;

import java.time.*;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TransportTestSamples {

    public static Transport getTransportSample1() {
        TransportId id = new TransportId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
        TransportStartTime transportStartTime = new TransportStartTime(getZonedDateTime(2024,5,17,10,0,0));
        TransportEndTime transportEndTime = new TransportEndTime(getZonedDateTime(2024,5,17,11,0,0));
        Location location = LocationTestSamples.getLocationSample1();
        Driver driver = DriverTestSamples.getDriverSample1();
        ServiceRequest serviceRequest = ServiceRequestTestSamples.getServiceRequestSample1();

        return new Transport(id, transportStartTime, transportEndTime, location, driver, serviceRequest);
    }

    public static Transport getTransportSample2() {
        TransportId id = new TransportId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
        TransportStartTime transportStartTime = new TransportStartTime(getZonedDateTime(2024,5,17,15,0,0));
        TransportEndTime transportEndTime = new TransportEndTime(getZonedDateTime(2024,5,17,16,0,0));
        Location location = LocationTestSamples.getLocationSample2();
        Driver driver = DriverTestSamples.getDriverSample2();
        ServiceRequest serviceRequest = ServiceRequestTestSamples.getServiceRequestSample2();
        return new Transport(id, transportStartTime, transportEndTime, location, driver, serviceRequest);
    }

    public static Transport getTransportRandomSampleGenerator() {
        TransportId id = new TransportId();
        TransportStartTime transportStartTime = new TransportStartTime(getRandomZonedDateTime());
        TransportEndTime transportEndTime = new TransportEndTime(getRandomZonedDateTime());
        Location location = LocationTestSamples.getLocationRandomSampleGenerator();
        Driver driver = DriverTestSamples.getDriverRandomSampleGenerator();
        ServiceRequest serviceRequest = ServiceRequestTestSamples.getServiceRequestRandomSampleGenerator();
        return new Transport(id, transportStartTime, transportEndTime, location, driver, serviceRequest);
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

    public static  ZonedDateTime getZonedDateTime(int year, int month, int day, int hour, int minute, int second){
        LocalDate date = LocalDate.of(year, month, day );
        LocalTime time = LocalTime.of(hour, minute,second);
        ZoneId zone = ZoneId.of("Europe/Lisbon");

        return ZonedDateTime.of(date, time, zone);
    }
}
