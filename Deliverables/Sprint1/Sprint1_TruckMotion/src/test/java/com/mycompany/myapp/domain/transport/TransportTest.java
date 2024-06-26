package com.mycompany.myapp.domain.transport;

import static com.mycompany.myapp.domain.driver.DriverTestSamples.*;
import static com.mycompany.myapp.domain.location.LocationTestSamples.*;
import static com.mycompany.myapp.domain.serviceRequest.ServiceRequestTestSamples.*;
import static com.mycompany.myapp.domain.transport.TransportTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.application.controller.TestUtil;
import org.junit.jupiter.api.Test;

class TransportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transport.class);
        Transport transport1 = getTransportSample1();
        Transport transport2 = new Transport();
        assertThat(transport1).isNotEqualTo(transport2);

        transport2.setId(transport1.getId());
        assertThat(transport1).isEqualTo(transport2);

        transport2 = getTransportSample2();
        assertThat(transport1).isNotEqualTo(transport2);
    }

    @Test
    void locationTest() throws Exception {
        Transport transport = getTransportRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        transport.setLocation(locationBack);
        assertThat(transport.getLocation()).isEqualTo(locationBack);

        transport.location(null);
        assertThat(transport.getLocation()).isNull();
    }

    @Test
    void driverTest() throws Exception {
        Transport transport = getTransportRandomSampleGenerator();
        Driver driverBack = getDriverRandomSampleGenerator();

        transport.setDriver(driverBack);
        assertThat(transport.getDriver()).isEqualTo(driverBack);

        transport.driver(null);
        assertThat(transport.getDriver()).isNull();
    }

    @Test
    void serviceRequestTest() throws Exception {
        Transport transport = getTransportRandomSampleGenerator();
        ServiceRequest serviceRequestBack = getServiceRequestRandomSampleGenerator();

        transport.setServiceRequest(serviceRequestBack);
        assertThat(transport.getServiceRequest()).isEqualTo(serviceRequestBack);

        transport.serviceRequest(null);
        assertThat(transport.getServiceRequest()).isNull();
    }
}
