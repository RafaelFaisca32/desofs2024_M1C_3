package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.CustomerTestSamples.*;
import static com.mycompany.myapp.domain.DriverTestSamples.*;
import static com.mycompany.myapp.domain.ManagerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationUser.class);
        ApplicationUser applicationUser1 = getApplicationUserSample1();
        ApplicationUser applicationUser2 = new ApplicationUser();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);

        applicationUser2.setId(applicationUser1.getId());
        assertThat(applicationUser1).isEqualTo(applicationUser2);

        applicationUser2 = getApplicationUserSample2();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);
    }

    @Test
    void driverTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Driver driverBack = getDriverRandomSampleGenerator();

        applicationUser.setDriver(driverBack);
        assertThat(applicationUser.getDriver()).isEqualTo(driverBack);
        assertThat(driverBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.driver(null);
        assertThat(applicationUser.getDriver()).isNull();
        assertThat(driverBack.getApplicationUser()).isNull();
    }

    @Test
    void managerTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Manager managerBack = getManagerRandomSampleGenerator();

        applicationUser.setManager(managerBack);
        assertThat(applicationUser.getManager()).isEqualTo(managerBack);
        assertThat(managerBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.manager(null);
        assertThat(applicationUser.getManager()).isNull();
        assertThat(managerBack.getApplicationUser()).isNull();
    }

    @Test
    void customerTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        applicationUser.setCustomer(customerBack);
        assertThat(applicationUser.getCustomer()).isEqualTo(customerBack);
        assertThat(customerBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.customer(null);
        assertThat(applicationUser.getCustomer()).isNull();
        assertThat(customerBack.getApplicationUser()).isNull();
    }
}
