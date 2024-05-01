package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.DriverTestSamples.*;
import static com.mycompany.myapp.domain.TransportTestSamples.*;
import static com.mycompany.myapp.domain.TruckTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DriverTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Driver.class);
        Driver driver1 = getDriverSample1();
        Driver driver2 = new Driver();
        assertThat(driver1).isNotEqualTo(driver2);

        driver2.setId(driver1.getId());
        assertThat(driver1).isEqualTo(driver2);

        driver2 = getDriverSample2();
        assertThat(driver1).isNotEqualTo(driver2);
    }

    @Test
    void truckTest() throws Exception {
        Driver driver = getDriverRandomSampleGenerator();
        Truck truckBack = getTruckRandomSampleGenerator();

        driver.setTruck(truckBack);
        assertThat(driver.getTruck()).isEqualTo(truckBack);

        driver.truck(null);
        assertThat(driver.getTruck()).isNull();
    }

    @Test
    void applicationUserTest() throws Exception {
        Driver driver = getDriverRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        driver.setApplicationUser(applicationUserBack);
        assertThat(driver.getApplicationUser()).isEqualTo(applicationUserBack);

        driver.applicationUser(null);
        assertThat(driver.getApplicationUser()).isNull();
    }

    @Test
    void transportTest() throws Exception {
        Driver driver = getDriverRandomSampleGenerator();
        Transport transportBack = getTransportRandomSampleGenerator();

        driver.setTransport(transportBack);
        assertThat(driver.getTransport()).isEqualTo(transportBack);
        assertThat(transportBack.getDriver()).isEqualTo(driver);

        driver.transport(null);
        assertThat(driver.getTransport()).isNull();
        assertThat(transportBack.getDriver()).isNull();
    }
}
