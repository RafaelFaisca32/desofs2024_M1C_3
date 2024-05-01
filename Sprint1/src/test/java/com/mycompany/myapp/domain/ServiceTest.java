package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CustomerTestSamples.*;
import static com.mycompany.myapp.domain.LocationTestSamples.*;
import static com.mycompany.myapp.domain.ServiceStatusTestSamples.*;
import static com.mycompany.myapp.domain.ServiceTestSamples.*;
import static com.mycompany.myapp.domain.TransportTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Service.class);
        Service service1 = getServiceSample1();
        Service service2 = new Service();
        assertThat(service1).isNotEqualTo(service2);

        service2.setId(service1.getId());
        assertThat(service1).isEqualTo(service2);

        service2 = getServiceSample2();
        assertThat(service1).isNotEqualTo(service2);
    }

    @Test
    void locationTest() throws Exception {
        Service service = getServiceRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        service.setLocation(locationBack);
        assertThat(service.getLocation()).isEqualTo(locationBack);

        service.location(null);
        assertThat(service.getLocation()).isNull();
    }

    @Test
    void customerTest() throws Exception {
        Service service = getServiceRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        service.setCustomer(customerBack);
        assertThat(service.getCustomer()).isEqualTo(customerBack);

        service.customer(null);
        assertThat(service.getCustomer()).isNull();
    }

    @Test
    void serviceStatusTest() throws Exception {
        Service service = getServiceRandomSampleGenerator();
        ServiceStatus serviceStatusBack = getServiceStatusRandomSampleGenerator();

        service.addServiceStatus(serviceStatusBack);
        assertThat(service.getServiceStatuses()).containsOnly(serviceStatusBack);
        assertThat(serviceStatusBack.getService()).isEqualTo(service);

        service.removeServiceStatus(serviceStatusBack);
        assertThat(service.getServiceStatuses()).doesNotContain(serviceStatusBack);
        assertThat(serviceStatusBack.getService()).isNull();

        service.serviceStatuses(new HashSet<>(Set.of(serviceStatusBack)));
        assertThat(service.getServiceStatuses()).containsOnly(serviceStatusBack);
        assertThat(serviceStatusBack.getService()).isEqualTo(service);

        service.setServiceStatuses(new HashSet<>());
        assertThat(service.getServiceStatuses()).doesNotContain(serviceStatusBack);
        assertThat(serviceStatusBack.getService()).isNull();
    }

    @Test
    void transportTest() throws Exception {
        Service service = getServiceRandomSampleGenerator();
        Transport transportBack = getTransportRandomSampleGenerator();

        service.setTransport(transportBack);
        assertThat(service.getTransport()).isEqualTo(transportBack);
        assertThat(transportBack.getService()).isEqualTo(service);

        service.transport(null);
        assertThat(service.getTransport()).isNull();
        assertThat(transportBack.getService()).isNull();
    }
}
