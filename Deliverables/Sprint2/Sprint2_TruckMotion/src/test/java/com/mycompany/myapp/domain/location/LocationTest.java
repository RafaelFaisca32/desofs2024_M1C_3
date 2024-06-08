package com.mycompany.myapp.domain.location;

import static com.mycompany.myapp.domain.customer.CustomerTestSamples.*;
import static com.mycompany.myapp.domain.location.LocationTestSamples.*;
import static com.mycompany.myapp.domain.serviceRequest.ServiceRequestTestSamples.*;
import static com.mycompany.myapp.domain.transport.TransportTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.transport.Transport;
import com.mycompany.myapp.application.controller.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class LocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = getLocationSample1();
        Location location2 = new Location();
        assertThat(location1).isNotEqualTo(location2);

        location2 = new Location(location1);
        assertThat(location1).isEqualTo(location2);

        location2 = getLocationSample2();
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    void customerTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        location.updateCustomer(customerBack);
        assertThat(location.getCustomer()).isEqualTo(customerBack);

        location.updateCustomer(null);
        assertThat(location.getCustomer()).isNull();
    }

    @Test
    void serviceRequestTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        ServiceRequest serviceRequestBack = getServiceRequestRandomSampleGenerator();

        location.updateServiceRequest(new HashSet<>(Set.of(serviceRequestBack)));
        assertThat(location.getServiceRequests()).isEqualTo(serviceRequestBack);
        assertThat(serviceRequestBack.getLocation()).isEqualTo(location);

        location.updateServiceRequest(null);
        assertThat(location.getServiceRequests()).isNull();
        assertThat(serviceRequestBack.getLocation()).isNull();
    }

    @Test
    void transportTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Transport transportBack = getTransportRandomSampleGenerator();

        location.updateTransport(new HashSet<>(Set.of(transportBack)));
        assertThat(location.getTransports()).isEqualTo(transportBack);
        assertThat(transportBack.getLocation()).isEqualTo(location);

        location.updateTransport(null);
        assertThat(location.getTransports()).isNull();
        assertThat(transportBack.getLocation()).isNull();
    }
}
