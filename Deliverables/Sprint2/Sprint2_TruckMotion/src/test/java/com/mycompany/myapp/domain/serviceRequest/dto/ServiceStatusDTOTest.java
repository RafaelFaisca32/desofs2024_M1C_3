package com.mycompany.myapp.domain.serviceRequest.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.application.controller.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ServiceStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceStatusDTO.class);
        ServiceStatusDTO serviceStatusDTO1 = new ServiceStatusDTO();
        serviceStatusDTO1.setId(UUID.randomUUID());
        ServiceStatusDTO serviceStatusDTO2 = new ServiceStatusDTO();
        assertThat(serviceStatusDTO1).isNotEqualTo(serviceStatusDTO2);
        serviceStatusDTO2.setId(serviceStatusDTO1.getId());
        assertThat(serviceStatusDTO1).isEqualTo(serviceStatusDTO2);
        serviceStatusDTO2.setId(UUID.randomUUID());
        assertThat(serviceStatusDTO1).isNotEqualTo(serviceStatusDTO2);
        serviceStatusDTO1.setId(null);
        assertThat(serviceStatusDTO1).isNotEqualTo(serviceStatusDTO2);
    }
}
