package com.mycompany.myapp.domain.manager;

import static com.mycompany.myapp.domain.user.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.manager.ManagerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.application.controller.TestUtil;
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

    @Test
    void applicationUserTest() throws Exception {
        Manager manager = getManagerRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        manager.setApplicationUser(applicationUserBack);
        assertThat(manager.getApplicationUser()).isEqualTo(applicationUserBack);

        manager.applicationUser(null);
        assertThat(manager.getApplicationUser()).isNull();
    }
}
