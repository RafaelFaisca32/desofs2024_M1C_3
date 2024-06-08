package com.mycompany.myapp.domain.truck;

import static com.mycompany.myapp.domain.driver.DriverTestSamples.*;
import static com.mycompany.myapp.domain.truck.TruckTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.application.controller.TestUtil;
import org.junit.jupiter.api.Test;

class TruckTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Truck.class);
        Truck truck1 = getTruckSample1();
        Truck truck2 = new Truck();
        assertThat(truck1).isNotEqualTo(truck2);

        truck2 = new Truck(truck1);
        assertThat(truck1).isEqualTo(truck2);

        truck2 = getTruckSample2();
        assertThat(truck1).isNotEqualTo(truck2);
    }

    @Test
    void driverTest() throws Exception {
        Truck truck = getTruckRandomSampleGenerator();
        Driver driverBack = getDriverRandomSampleGenerator();

        truck.updateDriver(driverBack);
        assertThat(truck.getDriver()).isEqualTo(driverBack);
        assertThat(driverBack.getTruck()).isEqualTo(truck);

        truck.updateDriver(null);
        assertThat(truck.getDriver()).isNull();
        assertThat(driverBack.getTruck()).isNull();
    }
}
