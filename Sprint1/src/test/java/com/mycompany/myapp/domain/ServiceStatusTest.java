package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ServiceStatusTestSamples.*;
import static com.mycompany.myapp.domain.ServiceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceStatus.class);
        ServiceStatus serviceStatus1 = getServiceStatusSample1();
        ServiceStatus serviceStatus2 = new ServiceStatus();
        assertThat(serviceStatus1).isNotEqualTo(serviceStatus2);

        serviceStatus2.setId(serviceStatus1.getId());
        assertThat(serviceStatus1).isEqualTo(serviceStatus2);

        serviceStatus2 = getServiceStatusSample2();
        assertThat(serviceStatus1).isNotEqualTo(serviceStatus2);
    }

    @Test
    void serviceTest() throws Exception {
        ServiceStatus serviceStatus = getServiceStatusRandomSampleGenerator();
        Service serviceBack = getServiceRandomSampleGenerator();

        serviceStatus.setService(serviceBack);
        assertThat(serviceStatus.getService()).isEqualTo(serviceBack);

        serviceStatus.service(null);
        assertThat(serviceStatus.getService()).isNull();
    }
}
