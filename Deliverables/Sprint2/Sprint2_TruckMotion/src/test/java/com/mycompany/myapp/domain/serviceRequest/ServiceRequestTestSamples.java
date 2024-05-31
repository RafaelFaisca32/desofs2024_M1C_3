package com.mycompany.myapp.domain.serviceRequest;

import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.customer.CustomerTestSamples;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.location.LocationTestSamples;

import java.time.*;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ServiceRequestTestSamples {

    public static ServiceRequest getServiceRequestSample1() {
        ServiceRequestId serviceRequestId = new ServiceRequestId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
        ServiceRequestName serviceRequestName = new ServiceRequestName("serviceName1");
        ServiceRequestItems serviceRequestItems = new ServiceRequestItems("items1");
        ServiceRequestDate serviceRequestDate = new ServiceRequestDate(getZonedDateTime(2024,5,17,10,0,0));
        ServiceRequestPrice serviceRequestPrice = new ServiceRequestPrice();
        ServiceRequestTotalWeightOfItems serviceRequestTotalWeightOfItems = new ServiceRequestTotalWeightOfItems();
        Customer customer = CustomerTestSamples.getCustomerSample1();
        Location location = LocationTestSamples.getLocationSample1();

        return new ServiceRequest(serviceRequestId,serviceRequestItems,serviceRequestName,serviceRequestTotalWeightOfItems,serviceRequestPrice,serviceRequestDate,location,customer);
    }

    public static ServiceRequest getServiceRequestSample2() {
        ServiceRequestId serviceRequestId = new ServiceRequestId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
        ServiceRequestName serviceRequestName = new ServiceRequestName("serviceName2");
        ServiceRequestItems serviceRequestItems = new ServiceRequestItems("items2");
        ServiceRequestDate serviceRequestDate = new ServiceRequestDate(getZonedDateTime(2024,5,17,10,0,0));
        ServiceRequestPrice serviceRequestPrice = new ServiceRequestPrice();
        ServiceRequestTotalWeightOfItems serviceRequestTotalWeightOfItems = new ServiceRequestTotalWeightOfItems();
        Customer customer = CustomerTestSamples.getCustomerSample2();
        Location location = LocationTestSamples.getLocationSample2();
        return new ServiceRequest(serviceRequestId,serviceRequestItems,serviceRequestName,serviceRequestTotalWeightOfItems,serviceRequestPrice,serviceRequestDate,location,customer);
    }

    public static ServiceRequest getServiceRequestRandomSampleGenerator() {
        ServiceRequestId serviceRequestId = new ServiceRequestId(UUID.randomUUID());
        ServiceRequestName serviceRequestName = new ServiceRequestName(UUID.randomUUID().toString());
        ServiceRequestItems serviceRequestItems = new ServiceRequestItems(UUID.randomUUID().toString());
        ServiceRequestDate serviceRequestDate = new ServiceRequestDate(getRandomZonedDateTime());
        ServiceRequestPrice serviceRequestPrice = new ServiceRequestPrice();
        ServiceRequestTotalWeightOfItems serviceRequestTotalWeightOfItems = new ServiceRequestTotalWeightOfItems();
        Customer customer = CustomerTestSamples.getCustomerRandomSampleGenerator();
        Location location = LocationTestSamples.getLocationRandomSampleGenerator();
        return new ServiceRequest(serviceRequestId,serviceRequestItems,serviceRequestName,serviceRequestTotalWeightOfItems,serviceRequestPrice,serviceRequestDate,location,customer);
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
