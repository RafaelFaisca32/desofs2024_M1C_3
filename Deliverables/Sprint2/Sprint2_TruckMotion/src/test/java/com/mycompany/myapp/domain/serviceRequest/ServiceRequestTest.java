package com.mycompany.myapp.domain.serviceRequest;

import static com.mycompany.myapp.domain.customer.CustomerTestSamples.*;
import static com.mycompany.myapp.domain.location.LocationTestSamples.*;
import static com.mycompany.myapp.domain.serviceRequest.ServiceRequestTestSamples.*;
import static com.mycompany.myapp.domain.serviceRequest.ServiceStatusTestSamples.*;
import static com.mycompany.myapp.domain.transport.TransportTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.transport.Transport;
import com.mycompany.myapp.application.controller.TestUtil;
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

        serviceRequest2 = new ServiceRequest(serviceRequest1);
        assertThat(serviceRequest1).isEqualTo(serviceRequest2);

        serviceRequest2 = getServiceRequestSample2();
        assertThat(serviceRequest1).isNotEqualTo(serviceRequest2);
    }

    @Test
    void locationTest() throws Exception {
        ServiceRequest serviceRequest = getServiceRequestRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        serviceRequest.updateLocation(locationBack);
        assertThat(serviceRequest.getLocation()).isEqualTo(locationBack);

        serviceRequest.updateLocation(null);
        assertThat(serviceRequest.getLocation()).isNull();
    }

    @Test
    void customerTest() throws Exception {
        ServiceRequest serviceRequest = getServiceRequestRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        serviceRequest.updateCustomer(customerBack);
        assertThat(serviceRequest.getCustomer()).isEqualTo(customerBack);

        serviceRequest.updateCustomer(null);
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

        serviceRequest.updateServiceStatuses(new HashSet<>(Set.of(serviceStatusBack)));
        assertThat(serviceRequest.getServiceStatuses()).containsOnly(serviceStatusBack);
        assertThat(serviceStatusBack.getServiceRequest()).isEqualTo(serviceRequest);

        serviceRequest.updateServiceStatuses(new HashSet<>());
        assertThat(serviceRequest.getServiceStatuses()).doesNotContain(serviceStatusBack);
        assertThat(serviceStatusBack.getServiceRequest()).isNull();
    }

    @Test
    void transportTest() throws Exception {
        ServiceRequest serviceRequest = getServiceRequestRandomSampleGenerator();
        Transport transportBack = getTransportRandomSampleGenerator();

        serviceRequest.updateTransport(transportBack);
        assertThat(serviceRequest.getTransport()).isEqualTo(transportBack);
        assertThat(transportBack.getServiceRequest()).isEqualTo(serviceRequest);

        serviceRequest.updateTransport(null);
        assertThat(serviceRequest.getTransport()).isNull();
        assertThat(transportBack.getServiceRequest()).isNull();
    }
}
