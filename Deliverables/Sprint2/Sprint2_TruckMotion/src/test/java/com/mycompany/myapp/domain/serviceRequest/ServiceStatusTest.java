package com.mycompany.myapp.domain.serviceRequest;

import static com.mycompany.myapp.domain.serviceRequest.ServiceRequestTestSamples.*;
import static com.mycompany.myapp.domain.serviceRequest.ServiceStatusTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.application.controller.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceStatus.class);
        ServiceStatus serviceStatus1 = getServiceStatusSample1();
        ServiceStatus serviceStatus2 = new ServiceStatus();
        assertThat(serviceStatus1).isNotEqualTo(serviceStatus2);

        serviceStatus2 = new ServiceStatus(serviceStatus1);
        assertThat(serviceStatus1).isEqualTo(serviceStatus2);

        serviceStatus2 = getServiceStatusSample2();
        assertThat(serviceStatus1).isNotEqualTo(serviceStatus2);
    }

    @Test
    void serviceRequestTest() throws Exception {
        ServiceStatus serviceStatus = getServiceStatusRandomSampleGenerator();
        ServiceRequest serviceRequestBack = getServiceRequestRandomSampleGenerator();

        serviceStatus.updateServiceRequest(serviceRequestBack);
        assertThat(serviceStatus.getServiceRequest()).isEqualTo(serviceRequestBack);

        serviceStatus.updateServiceRequest(null);
        assertThat(serviceStatus.getServiceRequest()).isNull();
    }
}
