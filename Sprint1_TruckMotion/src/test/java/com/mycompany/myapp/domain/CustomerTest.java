package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.CustomerTestSamples.*;
import static com.mycompany.myapp.domain.LocationTestSamples.*;
import static com.mycompany.myapp.domain.ServiceRequestTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
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

        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);

        customer2 = getCustomerSample2();
        assertThat(customer1).isNotEqualTo(customer2);
    }

    @Test
    void applicationUserTest() throws Exception {
        Customer customer = getCustomerRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        customer.setApplicationUser(applicationUserBack);
        assertThat(customer.getApplicationUser()).isEqualTo(applicationUserBack);

        customer.applicationUser(null);
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

        customer.locations(new HashSet<>(Set.of(locationBack)));
        assertThat(customer.getLocations()).containsOnly(locationBack);
        assertThat(locationBack.getCustomer()).isEqualTo(customer);

        customer.setLocations(new HashSet<>());
        assertThat(customer.getLocations()).doesNotContain(locationBack);
        assertThat(locationBack.getCustomer()).isNull();
    }

    @Test
    void serviceRequestTest() throws Exception {
        Customer customer = getCustomerRandomSampleGenerator();
        ServiceRequest serviceRequestBack = getServiceRequestRandomSampleGenerator();

        customer.setServiceRequest(serviceRequestBack);
        assertThat(customer.getServiceRequest()).isEqualTo(serviceRequestBack);
        assertThat(serviceRequestBack.getCustomer()).isEqualTo(customer);

        customer.serviceRequest(null);
        assertThat(customer.getServiceRequest()).isNull();
        assertThat(serviceRequestBack.getCustomer()).isNull();
    }
}
