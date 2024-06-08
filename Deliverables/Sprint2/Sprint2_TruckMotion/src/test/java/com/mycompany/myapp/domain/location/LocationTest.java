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

class LocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = getLocationSample1();
        Location location2 = new Location();
        assertThat(location1).isNotEqualTo(location2);

        location2 = new Location(location1.getId(),location1.getCoord(),location1.getCustomer());
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

        location.updateServiceRequest(serviceRequestBack);
        assertThat(location.getServiceRequest()).isEqualTo(serviceRequestBack);
        assertThat(serviceRequestBack.getLocation()).isEqualTo(location);

        location.updateServiceRequest(null);
        assertThat(location.getServiceRequest()).isNull();
        assertThat(serviceRequestBack.getLocation()).isNull();
    }

    @Test
    void transportTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Transport transportBack = getTransportRandomSampleGenerator();

        location.updateTransport(transportBack);
        assertThat(location.getTransport()).isEqualTo(transportBack);
        assertThat(transportBack.getLocation()).isEqualTo(location);

        location.updateTransport(null);
        assertThat(location.getTransport()).isNull();
        assertThat(transportBack.getLocation()).isNull();
    }
}
