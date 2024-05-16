package com.mycompany.myapp.domain.manager;

import com.mycompany.myapp.domain.user.ApplicationUser;
import com.mycompany.myapp.domain.user.ApplicationUserTestSamples;

import java.util.UUID;

public class ManagerTestSamples {

    public static Manager getManagerSample1() {
        ManagerId id = new ManagerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
        ApplicationUser applicationUser = ApplicationUserTestSamples.getApplicationUserSample1();
        return new Manager(id,applicationUser);
    }

    public static Manager getManagerSample2() {
        ManagerId id = new ManagerId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
        ApplicationUser applicationUser = ApplicationUserTestSamples.getApplicationUserSample2();
        return new Manager(id,applicationUser);
    }

    public static Manager getManagerRandomSampleGenerator() {
        ManagerId id = new ManagerId();
        ApplicationUser applicationUser = ApplicationUserTestSamples.getApplicationUserRandomSampleGenerator();
        return new Manager(id,applicationUser);
    }
}
