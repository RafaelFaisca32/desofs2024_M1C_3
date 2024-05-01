package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DriverTestSamples.*;
import static com.mycompany.myapp.domain.LocationTestSamples.*;
import static com.mycompany.myapp.domain.ServiceTestSamples.*;
import static com.mycompany.myapp.domain.TransportTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
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
    void serviceTest() throws Exception {
        Transport transport = getTransportRandomSampleGenerator();
        Service serviceBack = getServiceRandomSampleGenerator();

        transport.setService(serviceBack);
        assertThat(transport.getService()).isEqualTo(serviceBack);

        transport.service(null);
        assertThat(transport.getService()).isNull();
    }
}
