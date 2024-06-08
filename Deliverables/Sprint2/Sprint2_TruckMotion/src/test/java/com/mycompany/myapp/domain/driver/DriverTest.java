package com.mycompany.myapp.domain.driver;

import static com.mycompany.myapp.domain.user.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.driver.DriverTestSamples.*;
import static com.mycompany.myapp.domain.transport.TransportTestSamples.*;
import static com.mycompany.myapp.domain.truck.TruckTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.domain.transport.Transport;
import com.mycompany.myapp.domain.truck.Truck;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.application.controller.TestUtil;
import org.junit.jupiter.api.Test;

class DriverTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Driver.class);
        Driver driver1 = getDriverSample1();
        Driver driver2 = new Driver();
        assertThat(driver1).isNotEqualTo(driver2);

        driver2 = new Driver(driver1);
        assertThat(driver1).isEqualTo(driver2);

        driver2 = getDriverSample2();
        assertThat(driver1).isNotEqualTo(driver2);
    }

    @Test
    void truckTest() throws Exception {
        Driver driver = getDriverRandomSampleGenerator();
        Truck truckBack = getTruckRandomSampleGenerator();

        driver.updateTruck(truckBack);
        assertThat(driver.getTruck()).isEqualTo(truckBack);

        driver.updateTruck(null);
        assertThat(driver.getTruck()).isNull();
    }

    @Test
    void applicationUserTest() throws Exception {
        Driver driver = getDriverRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        driver.updateApplicationUser(applicationUserBack);
        assertThat(driver.getApplicationUser()).isEqualTo(applicationUserBack);

        driver.updateApplicationUser(null);
        assertThat(driver.getApplicationUser()).isNull();
    }

    @Test
    void transportTest() throws Exception {
        Driver driver = getDriverRandomSampleGenerator();
        Transport transportBack = getTransportRandomSampleGenerator();

        driver.updateTransport(transportBack);
        assertThat(driver.getTransport()).isEqualTo(transportBack);
        assertThat(transportBack.getDriver()).isEqualTo(driver);

        driver.updateTransport(null);
        assertThat(driver.getTransport()).isNull();
        assertThat(transportBack.getDriver()).isNull();
    }
}
