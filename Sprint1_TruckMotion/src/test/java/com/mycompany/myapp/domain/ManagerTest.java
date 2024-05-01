package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ManagerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManagerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Manager.class);
        Manager manager1 = getManagerSample1();
        Manager manager2 = new Manager();
        assertThat(manager1).isNotEqualTo(manager2);

        manager2.setId(manager1.getId());
        assertThat(manager1).isEqualTo(manager2);

        manager2 = getManagerSample2();
        assertThat(manager1).isNotEqualTo(manager2);
    }
}
