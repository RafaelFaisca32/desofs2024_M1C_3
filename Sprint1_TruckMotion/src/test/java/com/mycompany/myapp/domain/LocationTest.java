package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CustomerTestSamples.*;
import static com.mycompany.myapp.domain.LocationTestSamples.*;
import static com.mycompany.myapp.domain.ServiceRequestTestSamples.*;
import static com.mycompany.myapp.domain.TransportTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = getLocationSample1();
        Location location2 = new Location();
        assertThat(location1).isNotEqualTo(location2);

        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);

        location2 = getLocationSample2();
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    void customerTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        location.setCustomer(customerBack);
        assertThat(location.getCustomer()).isEqualTo(customerBack);

        location.customer(null);
        assertThat(location.getCustomer()).isNull();
    }

    @Test
    void serviceRequestTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        ServiceRequest serviceRequestBack = getServiceRequestRandomSampleGenerator();

        location.setServiceRequest(serviceRequestBack);
        assertThat(location.getServiceRequest()).isEqualTo(serviceRequestBack);
        assertThat(serviceRequestBack.getLocation()).isEqualTo(location);

        location.serviceRequest(null);
        assertThat(location.getServiceRequest()).isNull();
        assertThat(serviceRequestBack.getLocation()).isNull();
    }

    @Test
    void transportTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Transport transportBack = getTransportRandomSampleGenerator();

        location.setTransport(transportBack);
        assertThat(location.getTransport()).isEqualTo(transportBack);
        assertThat(transportBack.getLocation()).isEqualTo(location);

        location.transport(null);
        assertThat(location.getTransport()).isNull();
        assertThat(transportBack.getLocation()).isNull();
    }
}
