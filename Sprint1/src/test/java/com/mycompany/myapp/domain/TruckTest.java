package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DriverTestSamples.*;
import static com.mycompany.myapp.domain.TruckTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TruckTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Truck.class);
        Truck truck1 = getTruckSample1();
        Truck truck2 = new Truck();
        assertThat(truck1).isNotEqualTo(truck2);

        truck2.setId(truck1.getId());
        assertThat(truck1).isEqualTo(truck2);

        truck2 = getTruckSample2();
        assertThat(truck1).isNotEqualTo(truck2);
    }

    @Test
    void driverTest() throws Exception {
        Truck truck = getTruckRandomSampleGenerator();
        Driver driverBack = getDriverRandomSampleGenerator();

        truck.setDriver(driverBack);
        assertThat(truck.getDriver()).isEqualTo(driverBack);
        assertThat(driverBack.getTruck()).isEqualTo(truck);

        truck.driver(null);
        assertThat(truck.getDriver()).isNull();
        assertThat(driverBack.getTruck()).isNull();
    }
}
