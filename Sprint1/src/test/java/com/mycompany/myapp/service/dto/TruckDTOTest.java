package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TruckDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TruckDTO.class);
        TruckDTO truckDTO1 = new TruckDTO();
        truckDTO1.setId(UUID.randomUUID());
        TruckDTO truckDTO2 = new TruckDTO();
        assertThat(truckDTO1).isNotEqualTo(truckDTO2);
        truckDTO2.setId(truckDTO1.getId());
        assertThat(truckDTO1).isEqualTo(truckDTO2);
        truckDTO2.setId(UUID.randomUUID());
        assertThat(truckDTO1).isNotEqualTo(truckDTO2);
        truckDTO1.setId(null);
        assertThat(truckDTO1).isNotEqualTo(truckDTO2);
    }
}
