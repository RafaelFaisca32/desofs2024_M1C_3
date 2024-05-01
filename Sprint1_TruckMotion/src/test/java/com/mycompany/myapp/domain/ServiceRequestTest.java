package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CustomerTestSamples.*;
import static com.mycompany.myapp.domain.LocationTestSamples.*;
import static com.mycompany.myapp.domain.ServiceRequestTestSamples.*;
import static com.mycompany.myapp.domain.ServiceStatusTestSamples.*;
import static com.mycompany.myapp.domain.TransportTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServiceRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRequest.class);
        ServiceRequest serviceRequest1 = getServiceRequestSample1();
        ServiceRequest serviceRequest2 = new ServiceRequest();
        assertThat(serviceRequest1).isNotEqualTo(serviceRequest2);

        serviceRequest2.setId(serviceRequest1.getId());
        assertThat(serviceRequest1).isEqualTo(serviceRequest2);

        serviceRequest2 = getServiceRequestSample2();
        assertThat(serviceRequest1).isNotEqualTo(serviceRequest2);
    }

    @Test
    void locationTest() throws Exception {
        ServiceRequest serviceRequest = getServiceRequestRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        serviceRequest.setLocation(locationBack);
        assertThat(serviceRequest.getLocation()).isEqualTo(locationBack);

        serviceRequest.location(null);
        assertThat(serviceRequest.getLocation()).isNull();
    }

    @Test
    void customerTest() throws Exception {
        ServiceRequest serviceRequest = getServiceRequestRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        serviceRequest.setCustomer(customerBack);
        assertThat(serviceRequest.getCustomer()).isEqualTo(customerBack);

        serviceRequest.customer(null);
        assertThat(serviceRequest.getCustomer()).isNull();
    }

    @Test
    void serviceStatusTest() throws Exception {
        ServiceRequest serviceRequest = getServiceRequestRandomSampleGenerator();
        ServiceStatus serviceStatusBack = getServiceStatusRandomSampleGenerator();

        serviceRequest.addServiceStatus(serviceStatusBack);
        assertThat(serviceRequest.getServiceStatuses()).containsOnly(serviceStatusBack);
        assertThat(serviceStatusBack.getServiceRequest()).isEqualTo(serviceRequest);

        serviceRequest.removeServiceStatus(serviceStatusBack);
        assertThat(serviceRequest.getServiceStatuses()).doesNotContain(serviceStatusBack);
        assertThat(serviceStatusBack.getServiceRequest()).isNull();

        serviceRequest.serviceStatuses(new HashSet<>(Set.of(serviceStatusBack)));
        assertThat(serviceRequest.getServiceStatuses()).containsOnly(serviceStatusBack);
        assertThat(serviceStatusBack.getServiceRequest()).isEqualTo(serviceRequest);

        serviceRequest.setServiceStatuses(new HashSet<>());
        assertThat(serviceRequest.getServiceStatuses()).doesNotContain(serviceStatusBack);
        assertThat(serviceStatusBack.getServiceRequest()).isNull();
    }

    @Test
    void transportTest() throws Exception {
        ServiceRequest serviceRequest = getServiceRequestRandomSampleGenerator();
        Transport transportBack = getTransportRandomSampleGenerator();

        serviceRequest.setTransport(transportBack);
        assertThat(serviceRequest.getTransport()).isEqualTo(transportBack);
        assertThat(transportBack.getServiceRequest()).isEqualTo(serviceRequest);

        serviceRequest.transport(null);
        assertThat(serviceRequest.getTransport()).isNull();
        assertThat(transportBack.getServiceRequest()).isNull();
    }
}
