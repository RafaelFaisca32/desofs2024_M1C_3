package com.mycompany.myapp.domain.customer;

import static com.mycompany.myapp.domain.user.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.customer.CustomerTestSamples.*;
import static com.mycompany.myapp.domain.location.LocationTestSamples.*;
import static com.mycompany.myapp.domain.serviceRequest.ServiceRequestTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.application.controller.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = getCustomerSample1();
        Customer customer2 = new Customer();
        assertThat(customer1).isNotEqualTo(customer2);

        customer2 = new Customer(customer1.getId(),customer1.getCompany(),customer1.getApplicationUser());
        assertThat(customer1).isEqualTo(customer2);

        customer2 = getCustomerSample2();
        assertThat(customer1).isNotEqualTo(customer2);
    }

    @Test
    void applicationUserTest() throws Exception {
        Customer customer = getCustomerRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        customer.updateApplicationUser(applicationUserBack);
        assertThat(customer.getApplicationUser()).isEqualTo(applicationUserBack);

        customer.updateApplicationUser(null);
        assertThat(customer.getApplicationUser()).isNull();
    }

    @Test
    void locationTest() throws Exception {
        Customer customer = getCustomerRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        customer.addLocation(locationBack);
        assertThat(customer.getLocations()).containsOnly(locationBack);
        assertThat(locationBack.getCustomer()).isEqualTo(customer);

        customer.removeLocation(locationBack);
        assertThat(customer.getLocations()).doesNotContain(locationBack);
        assertThat(locationBack.getCustomer()).isNull();

        customer.updateLocations(new HashSet<>(Set.of(locationBack)));
        assertThat(customer.getLocations()).containsOnly(locationBack);
        assertThat(locationBack.getCustomer()).isEqualTo(customer);

        customer.updateLocations(new HashSet<>());
        assertThat(customer.getLocations()).doesNotContain(locationBack);
        assertThat(locationBack.getCustomer()).isNull();
    }

    @Test
    void serviceRequestTest() throws Exception {
        Customer customer = getCustomerRandomSampleGenerator();
        ServiceRequest serviceRequestBack = getServiceRequestRandomSampleGenerator();

        customer.updateServiceRequest(serviceRequestBack);
        assertThat(customer.getServiceRequest()).isEqualTo(serviceRequestBack);
        assertThat(serviceRequestBack.getCustomer()).isEqualTo(customer);

        customer.updateServiceRequest(null);
        assertThat(customer.getServiceRequest()).isNull();
        assertThat(serviceRequestBack.getCustomer()).isNull();
    }
}
