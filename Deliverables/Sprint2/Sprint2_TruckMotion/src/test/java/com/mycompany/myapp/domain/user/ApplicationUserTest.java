package com.mycompany.myapp.domain.user;

import static com.mycompany.myapp.domain.user.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.customer.CustomerTestSamples.*;
import static com.mycompany.myapp.domain.driver.DriverTestSamples.*;
import static com.mycompany.myapp.domain.manager.ManagerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.manager.Manager;
import com.mycompany.myapp.application.controller.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationUserTest {

    @Test
    void equalsVerifier()  {
        ApplicationUser applicationUser1 = getApplicationUserRandomSampleGenerator();
        ApplicationUser applicationUser2 = new ApplicationUser();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);

        applicationUser2 = new ApplicationUser(applicationUser1);
        assertThat(applicationUser1).isEqualTo(applicationUser2);

        applicationUser2 = getApplicationUserSample2();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);
    }

    @Test
    void driverTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Driver driverBack = getDriverRandomSampleGenerator();

        applicationUser.updateDriver(driverBack);
        assertThat(applicationUser.getDriver()).isEqualTo(driverBack);
        assertThat(driverBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.updateDriver(null);
        driverBack.updateApplicationUser(null);

        assertThat(applicationUser.getDriver()).isNull();
        assertThat(driverBack.getApplicationUser()).isNull();
    }

    @Test
    void managerTest()  {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Manager managerBack = new Manager();

        applicationUser.updateManager(managerBack);
        assertThat(applicationUser.getManager()).isEqualTo(managerBack);
        assertThat(managerBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.updateManager(null);
        managerBack.updateApplicationUser(null);

        assertThat(applicationUser.getManager()).isNull();
        assertThat(managerBack.getApplicationUser()).isNull();
    }

    @Test
    void customerTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        applicationUser.updateCustomer(customerBack);
        assertThat(applicationUser.getCustomer()).isEqualTo(customerBack);
        assertThat(customerBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.updateCustomer(null);
        customerBack.updateApplicationUser(null);

        assertThat(applicationUser.getCustomer()).isNull();
        assertThat(customerBack.getApplicationUser()).isNull();
    }
}
